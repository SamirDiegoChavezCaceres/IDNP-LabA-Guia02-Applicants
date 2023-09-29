package com.example.applicant

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.applicant.ui.theme.ApplicantTheme


@Composable
fun ApplicantFormApp(){
    ApplicantTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ){
            AddApplicantFormAppScreen()
        }
    }
}

