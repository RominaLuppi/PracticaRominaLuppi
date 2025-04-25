package com.example.core.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

    Scaffold(
        topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_inicio),
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            },
            navigationIcon = {
                Icon(
                  imageVector = Icons.Default.Home,
                  contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.padding(16.dp)
                    )
                },

        )
    }) { paddingScaffold ->

        Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingScaffold),
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        Divider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            color = Color.Gray,
            thickness = 1.5.dp,
            startIndent = 0.dp
        )

        Spacer(modifier = Modifier.height(42.dp))


        Row(modifier = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.Center)
        {
            Button(modifier = Modifier.width(150.dp),

                onClick = {
                    navController.navigate("FacturaScreen")
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.screen_fact_color))
            ) {
                Text(
                    text = stringResource(R.string.btn_home_facturas),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(modifier = Modifier.width(150.dp),

                onClick = {
                    navController.navigate("SmartSolarScreen")
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.screen_fact_color))
            ) {
                Text(
                    text = stringResource(R.string.btn_home_smart_solar),
                    color = Color.White
                )

            }
        }

    }
    }
}