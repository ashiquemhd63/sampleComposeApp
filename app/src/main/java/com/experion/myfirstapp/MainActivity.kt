package com.experion.myfirstapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.experion.myfirstapp.ui.theme.MyFirstAppTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.experion.myfirstapp.viewmodel.LoginViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, loginViewModel: LoginViewModel = LoginViewModel()) {

    var userEmail by remember { mutableStateOf(("")) }
    var password by remember { mutableStateOf(("")) }
    var showPassword by remember { mutableStateOf(false) }
    val loginState by loginViewModel.loginStateLiveData.observeAsState(false)
    var isLoginSuccess by remember { mutableStateOf(false) }
    var isLoginFailed by remember { mutableStateOf(false) }
    var isEmailInvalid by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
            ,

            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_flower),
                contentDescription = "abc",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .border(
                        BorderStroke(4.dp, Color.Yellow),
                        CircleShape
                    )
            )
        }

        Text(
            text = "Login",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 40.sp
            )

        )
        Text(
            text = "Email",
            modifier = modifier,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userEmail,
            onValueChange = { newText ->
                userEmail = newText
                isEmailInvalid = false
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

            placeholder = { Text(text = "Type your email") }
        )
        if (isEmailInvalid) {
            Text(
                text = "Invalid email format",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        //spacer used to give horizontal and vertical spacing
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Password",
            modifier = modifier,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 20.sp
            )

        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter password",
                )
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (password.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            showPassword = !showPassword
                        }
                    ) {
                        Icon(
                            painter = if (showPassword) painterResource(id = R.drawable.visibilitoff) else painterResource(
                                id = R.drawable.visibility
                            ),
                            contentDescription = if (showPassword) "Hide password" else "Show password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },

            )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail)
                        .matches()
                ) {
                    isEmailInvalid = true // Set the error flag when the email is invalid
                    return@Button
                }
                loginViewModel.isValidLoginData(userEmail, password)
                if (loginState) {
                    println("login state =>$loginState")
                    isLoginSuccess = true
                } else {
                    isLoginFailed = true
                }
                //your onclick code
            }, colors = ButtonDefaults.buttonColors(Color.DarkGray),
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(
                text = "Login",
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
        if (isLoginSuccess) {
//           Text(text = "Login Success")

            // Show the Snackbar when login is successful
            Snackbar(
                modifier = Modifier.padding(16.dp),

                content = { Text(text = "Login successful!") },
//               onDismiss = { isLoginSuccess = false }
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstAppTheme {
//        LoginScreen()
    }
}