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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.MainActivity
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.CustomLineBigScreen
import com.example.asm_mvvm.ui.theme.CustomLineSmailScreen
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButtonWithImage
import com.example.asm_mvvm.ui.theme.MyButtonWithImageSmailScreen
import com.example.asm_mvvm.viewmodels.UserViewModel


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                when {
                    maxHeight >= 720.dp -> {
                        BigTabletScreenLogin()
                    }
                    maxHeight > 448.dp -> {
                        SmailTabletScreenLogin()
                    }
                    maxHeight > 400.dp -> {
                        BigPhoneScreenLogin()
                    }
                    else -> {
                        SmailPhoneScreenLogin()
                    }
                }
            }
        }
    }
}


@Composable
fun TitleLoginBigScreen() {
    Image(
        painter = painterResource(id = R.drawable.logoapp),
        contentDescription = "anh nen",
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "Login Account",
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
fun TitleLoginSmailScreen() {
    Image(
        painter = painterResource(id = R.drawable.logoapp),
        contentDescription = "anh nen",
        modifier = Modifier.size(100.dp),
        contentScale = ContentScale.Crop
    )
    Text(
        text = "Login Account",
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
fun InputLoginBigScreen(
    email: MutableState<String>,
    password: MutableState<String>,
    stateChange: MutableState<Int>,
    passwordVisible: MutableState<Boolean>,
    emailSave: String,
    passSave: String,
    checkSave: String
) {
    val iconEye: Painter = painterResource(id = R.drawable.iceye)
    val iconCloseEye: Painter = painterResource(id = R.drawable.iccloseye)
    // Username TextField
    OutlinedTextField(
        value =
        if (emailSave != "") {
            if (stateChange.value == 1) {
                email.value
            } else {
                if (checkSave == "save") {
                    emailSave
                } else {
                    ""
                }
            }
        } else {
            email.value
        },
        onValueChange = {
            stateChange.value = 1
            email.value = it
        },
        label = { Text("E-mail") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    // Password TextField
    OutlinedTextField(
        value =
        if (stateChange.value == 1) {
            password.value
        } else {
            if (checkSave == "save") {
                passSave
            } else {
                ""
            }
        },
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
fun InputLoginSmailScreen(
    email: MutableState<String>,
    password: MutableState<String>,
    stateChange: MutableState<Int>,
    passwordVisible: MutableState<Boolean>,
    emailSave: String,
    passSave: String,
    checkSave: String
) {
    val iconEye: Painter = painterResource(id = R.drawable.iceye)
    val iconCloseEye: Painter = painterResource(id = R.drawable.iccloseye)
    // Username TextField
    OutlinedTextField(
        value =
        if (emailSave != "") {
            if (stateChange.value == 1) {
                email.value
            } else {
                if (checkSave == "save") {
                    emailSave
                } else {
                    ""
                }
            }
        } else {
            email.value
        },
        onValueChange = {
            stateChange.value = 1
            email.value = it
        },
        label = { Text("E-mail") },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, end = 10.dp)
        ,
        shape = RoundedCornerShape(10.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    // Password TextField
    OutlinedTextField(
        value =
        if (stateChange.value == 1) {
            password.value
        } else {
            if (checkSave == "save") {
                passSave
            } else {
                ""
            }
        },
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
fun CheckBoxLoginBigScreen(checked: MutableState<Boolean>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(70.dp)
        ) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { checked.value = it }
            )
            Text(
                "Remember me", textAlign = TextAlign.Start, fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(70.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Forget Password?",
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun CheckBoxLoginSmailScreen(checked: MutableState<Boolean>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp)
        ) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { checked.value = it }
            )
            Text(
                "Remember me", textAlign = TextAlign.Start, fontSize = 13.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Forget Password?",
                textDecoration = TextDecoration.Underline,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun NextSignUpBigScreen() {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "Don't have an account?", fontSize = 18.sp)
        Text(
            text = "Sign up",
            textDecoration = TextDecoration.Underline,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable {
                    val intent = Intent(context, SignUpActivity::class.java)
                    context.startActivity(intent)
                }
        )
    }
}

@Composable
fun NextSignUpSmailScreen() {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(5.dp)
    ) {
        Text(text = "Don't have an account?", fontSize = 13.sp)
        Text(
            text = "Sign up",
            textDecoration = TextDecoration.Underline,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable {
                    val intent = Intent(context, SignUpActivity::class.java)
                    context.startActivity(intent)
                }
        )
    }
}

@Composable
fun OtherMethodLoginBigScreen() {
    MyButtonWithImage(
        title = "Log In with Google",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.icongoogle
    )

    MyButtonWithImage(
        title = "Log In with Facebook",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.iconfacebook
    )
}

@Composable
fun OtherMethodLoginSmailScreen() {
    MyButtonWithImageSmailScreen(
        title = "Log In with Google",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.icongoogle
    )

    MyButtonWithImageSmailScreen(
        title = "Log In with Facebook",
        onClick = { /*TODO*/ },
        mauChu = Color.Black,
        mauNen = Color.White,
        image = R.drawable.iconfacebook
    )
}

@Composable
fun BigPhoneScreenLogin() {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)

    val userViewModel = UserViewModel()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val checked = remember { mutableStateOf(false) }
    val stateChange = remember { mutableIntStateOf(0) }

    val emailSave = userViewModel.getEmailFromSharedPreferences()
    val passSave = userViewModel.getPassFromSharedPreferences()
    val checkSave = userViewModel.getCheckFromSharedPreferences()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleLoginBigScreen()
        Spacer(modifier = Modifier.height(8.dp))
        InputLoginBigScreen(
            email = email,
            password = password,
            stateChange = stateChange,
            passwordVisible = passwordVisible,
            emailSave = emailSave ?: "",
            passSave = passSave ?: "",
            checkSave = checkSave ?: ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckBoxLoginBigScreen(checked = checked)
        Spacer(modifier = Modifier.height(8.dp))
        MyButton(
            title = "Log in",
            onClick = {
                userViewModel.login(
                    context,
                    if (stateChange.intValue == 1) {
                        email.value
                    } else {
                        emailSave.toString()
                    },
                    if (stateChange.intValue == 1) {
                        password.value
                    } else {
                        passSave.toString()
                    },
                    if (checked.value) {
                        "save"
                    } else {
                        "noSave"
                    },
                ) { success ->
                    if (success) {
                        Toast.makeText(
                            context,
                            "Login success",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        Toast.makeText(
                            context,
                            "Wrong email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            mauChu = Color.White,
            mauNen = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        NextSignUpBigScreen()
        Spacer(modifier = Modifier.height(8.dp))
        CustomLineBigScreen()
        OtherMethodLoginBigScreen()
    }
}

@Composable
fun SmailPhoneScreenLogin() {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)

    val userViewModel = UserViewModel()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val checked = remember { mutableStateOf(false) }
    val stateChange = remember { mutableIntStateOf(0) }

    val emailSave = userViewModel.getEmailFromSharedPreferences()
    val passSave = userViewModel.getPassFromSharedPreferences()
    val checkSave = userViewModel.getCheckFromSharedPreferences()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleLoginSmailScreen()
        Spacer(modifier = Modifier.height(8.dp))
        InputLoginSmailScreen(
            email = email,
            password = password,
            stateChange = stateChange,
            passwordVisible = passwordVisible,
            emailSave = emailSave ?: "",
            passSave = passSave ?: "",
            checkSave = checkSave ?: ""
        )
        CheckBoxLoginSmailScreen(checked = checked)
        MyButton(
            title = "Log in",
            onClick = {
                userViewModel.login(
                    context,
                    if (stateChange.intValue == 1) {
                        email.value
                    } else {
                        emailSave.toString()
                    },
                    if (stateChange.intValue == 1) {
                        password.value
                    } else {
                        passSave.toString()
                    },
                    if (checked.value) {
                        "save"
                    } else {
                        "noSave"
                    },
                ) { success ->
                    if (success) {
                        Toast.makeText(
                            context,
                            "Login success",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        Toast.makeText(
                            context,
                            "Wrong email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            mauChu = Color.White,
            mauNen = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        NextSignUpSmailScreen()
        CustomLineSmailScreen()
        OtherMethodLoginSmailScreen()
    }
}

@Composable
fun BigTabletScreenLogin() {
    val context = LocalContext.current
}

@Composable
fun SmailTabletScreenLogin() {
    val context = LocalContext.current
}