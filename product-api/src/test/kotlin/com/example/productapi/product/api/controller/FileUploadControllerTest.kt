package com.example.productapi.product.api.controller

import com.example.productapi.product.application.S3Service
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.partWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParts
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(value = [RestDocumentationExtension::class])
@AutoConfigureRestDocs(uriHost = "api.test.com", uriPort = 8080)
@WebMvcTest(controllers = [FileUploadController::class])
class FileUploadControllerTest @Autowired constructor(

    @Autowired
    val mockMvc: MockMvc,

    @MockkBean
    val s3Service: S3Service
) {

    @Test
    @DisplayName("[POST] [/files/upload] 이미지 업로드")
    fun uploadFile() {

        val mockFile = MockMultipartFile("file", "originalFileName", "image/jpeg", "mockFileContent".toByteArray())
        every { s3Service.uploadImage(mockFile) } returns "https://localhost"

        mockMvc.perform(multipart("/files/upload").file(mockFile))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.handler().methodName("uploadFile"))
            .andDo(
                document(
                    "file-upload",
                    requestParts(partWithName("file").description("업로드할 이미지 파일")),
                    responseFields(fieldWithPath("imagePath").description("업로드된 이미지의 URL"))
                )
            )
    }
}
