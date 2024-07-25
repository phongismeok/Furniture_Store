package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.example.asm_mvvm.ui.theme.CustomLineBigScreen
import com.example.asm_mvvm.ui.theme.CustomLineSmailScreen
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButtonSmailScreen
import com.example.asm_mvvm.ui.theme.MyButtonWithImage
import com.example.asm_mvvm.ui.theme.MyButtonWithImageSmailScreen
import com.example.asm_mvvm.viewmodels.UserViewModel

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                if (maxWidth >= 720.dp) {
                    BigTabletScreenSignUp()
                } else if (maxWidth < 720.dp && maxWidth > 448.dp) {
                    SmailTabletScreenSignUp()
                } else if (maxWidth <= 448.dp && maxWidth > 360.dp) {
                    BigPhoneScreenSignUp()
                } else {
                    SmailPhoneScreenSignUp()
                }
            }
        }
    }
}

@Composable
fun TitleSignUpBigScreen () {
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
}

@Composable
fun TitleSignUpSmailScreen () {
    Image(
        painter = painterResource(id = R.drawable.logoapp),
        contentDescription = "anh nen",
        modifier = Modifier.size(70.dp),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "Create Account",
        style = TextStyle(
            fontSize = 22.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        ),
        modifier = Modifier.padding(bottom = 5.dp)
    )
}

@Composable
fun InputSignUpBigScreen (
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    checked: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
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
            .padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun InputSignUpSmailScreen (
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    checked: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
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
            .height(60.dp)
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
    )
// Email TextField
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("E-mail") },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
    )
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
            .height(60.dp)
            .padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun ConfirmCheckSignUpBigScreen (checked: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
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
}

@Composable
fun ConfirmCheckSignUpSmailScreen (checked: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it },
        )
        Text(
            " I agree with", fontSize = 11.sp
        )
        Text(
            " Privacy Policy",
            fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            color = Color.Blue
        )
        Text(
            " and", fontSize = 11.sp
        )
        Text(
            " Terms & Conditions",
            fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            color = Color.Blue
        )
    }
}


@Composable
fun NextLoginBigScreen () {
    val context = LocalContext.current
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
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            })
        )
    }
}

@Composable
fun NextLoginSmailScreen () {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "Already have an account?", fontSize = 13.sp)
        Text(
            text = " Log in",
            textDecoration = TextDecoration.Underline,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            })
        )
    }
}

@Composable
fun OtherMethodSignUpBigScreen () {
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
}

@Composable
fun OtherMethodSignUpSmailScreen () {
    MyButtonWithImageSmailScreen(
        title = "Sign Up with Google",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.icongoogle
    )

    MyButtonWithImageSmailScreen(
        title = "Sign Up with Facebook",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.iconfacebook
    )
}

@Composable
fun BigPhoneScreenSignUp() {
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
            .background(Color.White).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleSignUpBigScreen()
        InputSignUpBigScreen(
            username = username,
            email = email,
            password = password,
            checked = checked,
            passwordVisible = passwordVisible
        )
        ConfirmCheckSignUpBigScreen(checked = checked)
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
            mauNen = Color.Gray
        )
        NextLoginBigScreen()
        CustomLineBigScreen()
        OtherMethodSignUpBigScreen()
    }
}

@Composable
fun SmailPhoneScreenSignUp() {
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
        TitleSignUpSmailScreen()
        InputSignUpSmailScreen(
            username = username,
            email = email,
            password = password,
            checked = checked,
            passwordVisible = passwordVisible
        )
        ConfirmCheckSignUpSmailScreen(checked = checked)
        MyButtonSmailScreen(
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
            mauNen = Color.Gray
        )
        NextLoginSmailScreen()
        CustomLineSmailScreen()
        OtherMethodSignUpSmailScreen()
    }
}

@Composable
fun BigTabletScreenSignUp() {
    val context = LocalContext.current
}

@Composable
fun SmailTabletScreenSignUp() {
    val context = LocalContext.current
}


