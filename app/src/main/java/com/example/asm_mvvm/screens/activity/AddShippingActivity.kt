package com.example.asm_mvvm.screens.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.models.District
import com.example.asm_mvvm.models.Province
import com.example.asm_mvvm.models.Ward
import com.example.asm_mvvm.request.ShippingRequest
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.GhnViewModel
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class AddShippingActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ghnViewModel = GhnViewModel()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyToolbar3(title = "Add shipping address")
                IconAnimation()
                DropDown(ghnViewModel = ghnViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun IconAnimation() {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.home)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(ghnViewModel: GhnViewModel) {
    val provinces by ghnViewModel.provinces.observeAsState(emptyList())
    ghnViewModel.fetchProvinces()

    var expanded by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf<Province?>(null) }

    var expandedDistrict by remember { mutableStateOf(false) }
    var selectedDistrict by remember { mutableStateOf<District?>(null) }

    var expandedWard by remember { mutableStateOf(false) }
    var selectedWard by remember { mutableStateOf<Ward?>(null) }

    var idProvince by remember { mutableIntStateOf(0) }
    var idDistrict by remember { mutableIntStateOf(0) }

    var provinceName by remember {
        mutableStateOf("")
    }
    var districtName by remember {
        mutableStateOf("")
    }
    var wardName by remember {
        mutableStateOf("")
    }

    var dataAddress by remember {
        mutableStateOf("")
    }


    val districts by ghnViewModel.districts.observeAsState(emptyList())
    ghnViewModel.fetchDistricts(idProvince)

    val wards by ghnViewModel.wards.observeAsState(emptyList())
    ghnViewModel.fetchWards(idDistrict)

    Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Column(modifier = Modifier.padding(top = 40.dp)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(60.dp),
                    readOnly = true,
                    value = selectedProvince?.ProvinceName ?: "Select Province",
                    onValueChange = {},
                    label = { Text("Province") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    provinces.forEach { province ->
                        DropdownMenuItem(
                            text = { Text(province.ProvinceName) },
                            onClick = {
                                selectedProvince = province
                                expanded = false
                                idProvince = province.ProvinceID
                                provinceName = province.ProvinceName
                            },
                        )
                    }
                }
            }
        }
        //
        Column(modifier = Modifier.padding(top = 30.dp)) {
            ExposedDropdownMenuBox(
                expanded = expandedDistrict,
                onExpandedChange = { expandedDistrict = !expandedDistrict },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(60.dp),
                    readOnly = true,
                    value = selectedDistrict?.DistrictName ?: "Select District",
                    onValueChange = {},
                    label = { Text("District") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDistrict) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedDistrict,
                    onDismissRequest = { expandedDistrict = false },
                ) {
                    districts.forEach { district ->
                        DropdownMenuItem(
                            text = { Text(district.DistrictName) },
                            onClick = {
                                selectedDistrict = district
                                expandedDistrict = false
                                idDistrict = district.DistrictID
                                districtName = district.DistrictName
                            },
                        )
                    }
                }
            }
        }
        //
        Column(modifier = Modifier.padding(top = 30.dp)) {
            ExposedDropdownMenuBox(
                expanded = expandedWard,
                onExpandedChange = { expandedWard = !expandedWard },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(60.dp),
                    readOnly = true,
                    value = selectedWard?.WardName ?: "Select Ward",
                    onValueChange = {},
                    label = { Text("Ward") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWard) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedWard,
                    onDismissRequest = { expandedWard = false },
                ) {
                    wards.forEach { ward ->
                        DropdownMenuItem(
                            text = { Text(ward.WardName) },
                            onClick = {
                                selectedWard = ward
                                expandedWard = false
                                wardName = ward.WardName
                                dataAddress = "$provinceName, $districtName, $wardName"
                            },
                        )
                    }
                }
            }
        }
        FormInput(addressDetail = dataAddress)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(addressDetail: String) {
    val shippingViewModel = ShippingViewModel()
    var fullname by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current
    val userViewModel = UserViewModel()
    SharedPreferencesManager.init(context)
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.75f),
        ) {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Địa chỉ chi tiết") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, top = 20.dp)
                    .height(65.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
            )

            OutlinedTextField(
                value = fullname,
                onValueChange = { fullname = it },
                label = { Text("Full name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(65.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                ),
            )
        }

        Column {
            Button(
                onClick = {
                    val shipBody = ShippingRequest(
                        name = fullname,
                        address = addressDetail,
                        addressDetail = address,
                        account = account,
                        select = 0,
                    )
                    shippingViewModel.addProductToShipping(
                        shipBody,
                        "Thêm địa chỉ thành công",
                        "Thêm địa chỉ thất bại",
                        context
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(10)
            ) {
                Text(text = "SAVE ADDRESS", fontSize = 25.sp, fontWeight = FontWeight(500))
            }
        }

    }
}


