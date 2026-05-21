package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.ResearchListResponseDto;
import com.example.komtekProject.dto.ResearchResultDto;
import com.example.komtekProject.dto.ResearchResultUploadDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Research;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.event.OrderStatusChangedEvent;
import com.example.komtekProject.exception.InvalidResearchResultException;
import com.example.komtekProject.exception.InvalidStatusTransitionException;
import com.example.komtekProject.exception.OrderNotFoundException;
import com.example.komtekProject.exception.ResearchNotFoundException;
import com.example.komtekProject.mapper.ResearchMapper;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.service.ResearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final OrderRepository orderRepository;
    private final ResearchMapper researchMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public ResearchListResponseDto getResearchesByOrderId(Long orderId) {
        log.debug("Получение списка исследований для Order ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена", orderId);
                    return new OrderNotFoundException(orderId);
                });

        ResearchListResponseDto dto = new ResearchListResponseDto();
        dto.setResearches(researchMapper.toDtoList(order.getResearches()));
        return dto;
    }

    @Override
    @Transactional
    public ResearchListResponseDto uploadResults(Long orderId, ResearchResultUploadDto uploadDto) {
        log.info("Загрузка результатов для Order ID: {}, количество: {}",
                orderId, uploadDto.getResearches().size());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена", orderId);
                    return new OrderNotFoundException(orderId);
                });

        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            log.warn("Попытка загрузить результаты для Order ID: {} в статусе {}",
                    orderId, order.getStatus());
            throw new InvalidStatusTransitionException(order.getStatus(), OrderStatus.COMPLETED);
        }

        Map<Long, Research> researchById = order.getResearches().stream()
                .collect(Collectors.toMap(Research::getId, r -> r));

        for (ResearchResultDto resultDto : uploadDto.getResearches()) {
            Research research = researchById.get(resultDto.getId());

            if (research == null) {
                log.warn("У направления {} не найдено исследование с id {}", orderId, resultDto.getId());
                throw new ResearchNotFoundException(orderId, resultDto.getId());
            }

            validateResult(resultDto);

            research.applyResult(
                    resultDto.getNumberResult(),
                    resultDto.getUnit(),
                    resultDto.getTextResult(),
                    resultDto.getConclusion()
            );

            log.debug("Применён результат для Research ID: {}", research.getId());
        }

        boolean allCompleted = order.getResearches().stream().allMatch(Research::hasResult);

        if (allCompleted) {
            log.info("Все исследования Order ID: {} завершены. Переводим в COMPLETED", orderId);
            order.setStatus(OrderStatus.COMPLETED);

            eventPublisher.publishEvent(new OrderStatusChangedEvent(
                    order.getId(),
                    OrderStatus.COMPLETED,
                    order.getCreatorOrganization().getEmail()
            ));
        }

        orderRepository.save(order);

        ResearchListResponseDto dto = new ResearchListResponseDto();
        dto.setResearches(researchMapper.toDtoList(order.getResearches()));
        return dto;
    }

    private void validateResult(ResearchResultDto dto) {
        boolean hasNumber = dto.getNumberResult() != null;
        boolean hasText = dto.getTextResult() != null && !dto.getTextResult().isBlank();

        if (!hasNumber && !hasText) {
            throw new InvalidResearchResultException(
                    "Для загрузки результата необходимо указать данные в одном из полей: numberResult, textResult"
            );
        }

        if (hasNumber && hasText) {
            throw new InvalidResearchResultException(
                    "Нельзя одновременно передавать numberResult и textResult"
            );
        }
        if (hasNumber && (dto.getUnit() == null || dto.getUnit().isBlank())) {
            throw new InvalidResearchResultException(
                    "При передаче числового результата необходимо передавать данные о единице измерения"
            );
        }
    }
}