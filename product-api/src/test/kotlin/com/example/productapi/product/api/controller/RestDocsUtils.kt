package com.example.productapi.product.api.controller


import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Snippet

class RestDocsUtils {

    companion object {
        fun prettyDocument(identifier: String, vararg snippets: Snippet): RestDocumentationResultHandler {
            return document(
                identifier,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                *snippets
            )
        }
    }
}
