package com.example.shriganeshtradelink

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun Accountscreen(
    modifier: Modifier=Modifier
){
    Surface(
        modifier=modifier.fillMaxSize()
    ) {
        Column {
            Row (
                modifier=modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(modifier = modifier
                    .weight(.2f)
                    .aspectRatio(1f)
                    .padding(10.dp) ,
                    contentAlignment = Alignment.Center){
                    Image(imageVector = Icons.Default.AccountCircle, contentDescription =null,
                        contentScale = ContentScale.Crop
                    , modifier = modifier.fillMaxSize())
                }
                Spacer(modifier = modifier.width(5.dp))
                Column (
                    modifier.weight(.5f)
                ){
                    Text(text = "Gourav Agarwal",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(600),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "7001805799",
                        style = MaterialTheme.typography.bodySmall)
                }

            }
            Divider()
        }
    }
}