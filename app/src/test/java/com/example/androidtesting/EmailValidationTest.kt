package com.example.androidtesting

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class EmailValidatorTest {

    private lateinit var emailValidator: EmailValidator

    @Before
    fun setup() {
        emailValidator = EmailValidator()
    }

    @Test
        fun isValidEmail_WhenCorrectEmail_ShouldReturnTrue() {
        // Given
        val email = "test@test.co"

        // When
        val isValid = emailValidator.isValidEmail(email)

        // Then
        assertThat(isValid).isTrue()
    }
}