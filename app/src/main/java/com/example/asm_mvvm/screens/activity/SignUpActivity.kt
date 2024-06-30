package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButtonWithImage

class SignUpActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            var checked by remember { mutableStateOf(true) }
            val icon1: Painter = painterResource(id = R.drawable.iceye)
            val icon2: Painter = painterResource(id = R.drawable.iccloseye)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoapp),
                    contentDescription = "anh nen",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Create Account",
                    style = TextStyle(
                        fontSize = 35.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
                // Username TextField
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Username TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) icon1 else icon2
                        val description =
                            if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = description)
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    Text(
                        " I agree with", fontSize = 16.sp
                    )
                    Text(
                        " Privacy Policy",
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    )
                    Text(
                        " and", fontSize = 16.sp
                    )
                    Text(
                        " Terms & Conditions",
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    )
                }

                MyButton(
                    title = "Sign Up",
                    onClick = { /*TODO*/ },
                    mauChu = Color.White,
                    mauNen = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Already have an account?", fontSize = 18.sp)
                    Text(
                        text = " Log in",
                        textDecoration = TextDecoration.Underline,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = {
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            this@SignUpActivity.startActivity(intent)
                        })
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Divider(
                        color = Color(0xFF997777),
                        thickness = 1.dp,
                        modifier = Modifier.weight(0.1f)
                    )

                    Text(text = "or", fontSize = 18.sp)

                    Divider(
                        color = Color(0xFF997777),
                        thickness = 1.dp,
                        modifier = Modifier.weight(0.1f)
                    )
                }

                MyButtonWithImage(
                    title = "Sign Up with Google",
                    onClick = { /*TODO*/ },
                    mauChu = Color.Black,
                    mauNen = Color.White,
                    image = R.drawable.icongoogle
                )

                MyButtonWithImage(
                    title = "Sign Up with Facebook",
                    onClick = { /*TODO*/ },
                    mauChu = Color.Black,
                    mauNen = Color.White,
                    image = R.drawable.iconfacebook
                )
                //
            }
        }
    }
}