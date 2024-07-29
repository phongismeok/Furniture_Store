package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButtonWithImage
import com.example.asm_mvvm.viewmodels.UserViewModel

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SizeSignUpScreen()
        }
    }
}

@Composable
fun TitleSignUpScreen (type:String) {
    Image(
        painter = painterResource(id = R.drawable.logoapp),
        contentDescription = "anh nen",
        modifier = Modifier.size(
            when (type) {
                "large" -> {
                    200.dp
                }

                "fairly" -> {
                    130.dp
                }

                "medium" -> {
                    100.dp
                }

                else -> {
                    70.dp
                }
            }
        ),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "Create Account",
        style = TextStyle(
            fontSize =
            when (type) {
                "large" -> {
                    35.sp
                }

                "fairly" -> {
                    30.sp
                }

                "medium" -> {
                    25.sp
                }

                else -> {
                    22.sp
                }
            },
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        ),
        modifier = Modifier.padding(bottom =
        when (type) {
            "large" -> {
                30.dp
            }

            "fairly" -> {
                20.dp
            }

            "medium" -> {
                10.dp
            }

            else -> {
                5.dp
            }
        })
    )
}

@Composable
fun InputSignUpScreen (
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    checked: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
    type: String
) {
    val iconEye: Painter = painterResource(id = R.drawable.iceye)
    val iconCloseEye: Painter = painterResource(id = R.drawable.iccloseye)
    // Username TextField
    OutlinedTextField(
        value = username.value,
        onValueChange = { username.value = it },
        label = { Text("Full Name") },
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }

                    "fairly" -> {
                        65.dp
                    }

                    "medium" -> {
                        62.dp
                    }

                    else -> {
                        60.dp
                    }
                }
            )
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
// Username TextField
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("E-mail") },
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }

                    "fairly" -> {
                        65.dp
                    }

                    "medium" -> {
                        62.dp
                    }

                    else -> {
                        60.dp
                    }
                }
            )
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    // Password TextField
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value) iconEye else iconCloseEye
            val description =
                if (passwordVisible.value) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = image, contentDescription = description)
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }

                    "fairly" -> {
                        65.dp
                    }

                    "medium" -> {
                        62.dp
                    }

                    else -> {
                        60.dp
                    }
                }
            )
            .padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun ConfirmCheckSignUpScreen (type: String,checked: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }

                    "fairly" -> {
                        65.dp
                    }

                    "medium" -> {
                        55.dp
                    }

                    else -> {
                        50.dp
                    }
                }
            )
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
        )
        Text(
            " I agree with", fontSize =
            when (type) {
                "large" -> {
                    16.sp
                }

                "fairly" -> {
                    14.sp
                }

                "medium" -> {
                    12.sp
                }

                else -> {
                    11.sp
                }
            }
        )
        Text(
            " Privacy Policy",
            fontSize =
            when (type) {
                "large" -> {
                    16.sp
                }

                "fairly" -> {
                    14.sp
                }

                "medium" -> {
                    12.sp
                }

                else -> {
                    11.sp
                }
            },
            textDecoration = TextDecoration.Underline,
            color = Color.Blue
        )
        Text(
            " and", fontSize =
            when (type) {
                "large" -> {
                    16.sp
                }

                "fairly" -> {
                    14.sp
                }

                "medium" -> {
                    12.sp
                }

                else -> {
                    11.sp
                }
            }
        )
        Text(
            " Terms & Conditions",
            fontSize =
            when (type) {
                "large" -> {
                    16.sp
                }

                "fairly" -> {
                    14.sp
                }

                "medium" -> {
                    12.sp
                }

                else -> {
                    11.sp
                }
            },
            textDecoration = TextDecoration.Underline,
            color = Color.Blue
        )
    }
}

@Composable
fun NextLoginScreen (type: String) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "Already have an account?", fontSize =
        when (type) {
            "large" -> {
                18.sp
            }

            "fairly" -> {
                16.sp
            }

            "medium" -> {
                14.sp
            }

            else -> {
                13.sp
            }
        })
        Text(
            text = " Log in",
            textDecoration = TextDecoration.Underline,
            fontSize =
            when (type) {
                "large" -> {
                    18.sp
                }

                "fairly" -> {
                    16.sp
                }

                "medium" -> {
                    14.sp
                }

                else -> {
                    13.sp
                }
            },
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            })
        )
    }
}

@Composable
fun ScreenSignUp(type: String) {
    val context = LocalContext.current
    val userViewModel = UserViewModel()
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val checked = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleSignUpScreen(type = type)
        InputSignUpScreen(
            username = username,
            email = email,
            password = password,
            checked = checked,
            passwordVisible = passwordVisible,
            type = type
        )
        ConfirmCheckSignUpScreen(type = type, checked = checked)
        MyButton(
            title = "Sign Up",
            onClick = {
                userViewModel.signUp(email.value, password.value) { success ->
                    if (success) {
                        Toast.makeText(context,"Sign up success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,"Sign up fail", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            mauChu = Color.White,
            mauNen = Color.Gray,
            type = type
        )
        NextLoginScreen(type = type)
//        CustomLineScreen()
        MyButtonWithImage(
            title = "Log In with Google",
            onClick = { /*TODO*/ },
            mauChu = Color.Black,
            mauNen = Color.White,
            image = R.drawable.icongoogle,
            type = type
        )

        MyButtonWithImage(
            title = "Log In with Facebook",
            onClick = { /*TODO*/ },
            mauChu = Color.Black,
            mauNen = Color.White,
            image = R.drawable.iconfacebook,
            type = type
        )
    }
}

@Composable
fun SizeSignUpScreen() {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
//    val screenWidthPx = displayMetrics.widthPixels
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

//    val screenWidthDp = screenWidthPx / density
    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenSignUp(type = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenSignUp(type = "fairly")
    } else if (screenHeightDp > 714) {
        // medium
        ScreenSignUp(type = "medium")
    } else {
        // smail
        ScreenSignUp(type = "smail")
    }
}