package com.example.productdomain.common

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ImagePathTest {


    @Test
    @DisplayName("이미지 경로는 https:// 로 시작해야한다.")
    fun create() {
        val imagePath = ImagePath("https://localhost")

        assertThat(imagePath.value).isEqualTo("https://localhost")
    }

    @Test
    @DisplayName("이미지 경로는 https:// 로 시작하지 않으면 생성에 실패한다.")
    fun createFail() {
        assertThatCode { ImagePath("http://localhost") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미지 경로 형식이 올바르지 않습니다.")
    }
}
