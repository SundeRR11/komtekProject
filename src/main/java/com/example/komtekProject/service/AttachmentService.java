package com.example.komtekProject.service;

import com.example.komtekProject.dto.AttachmentRequestDto;
import com.example.komtekProject.dto.AttachmentResponseDto;

public interface AttachmentService {

    AttachmentResponseDto createAttachment(AttachmentRequestDto request);
}