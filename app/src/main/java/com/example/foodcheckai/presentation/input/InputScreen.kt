package com.example.foodcheckai.presentation.input

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodcheckai.presentation.input.components.AnalyzeButton
import com.example.foodcheckai.presentation.input.components.ImagePickerSection
import com.example.foodcheckai.presentation.input.components.IngredientTextField
import com.example.foodcheckai.presentation.input.components.OcrLoadingDialog
import com.example.foodcheckai.presentation.preview.ComponentPreview
import com.example.foodcheckai.presentation.preview.CompletePreview
import com.example.foodcheckai.presentation.preview.PreviewData
import com.example.foodcheckai.presentation.theme.FoodCheckAITheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InputScreen(
    onNavigateBack: () -> Unit,
    onNavigateToResult: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InputViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onEvent(InputEvent.ImageSelected(it)) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraImageUri != null) {
            viewModel.onEvent(InputEvent.ImageSelected(cameraImageUri!!))
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera(context) { uri ->
                cameraImageUri = uri
                cameraLauncher.launch(uri)
            }
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onEvent(InputEvent.ClearError)
        }
    }

    LaunchedEffect(uiState.isAnalyzing) {
        if (uiState.isAnalyzing) {
            // Short delay to allow the ViewModel to finish formatting the text
            // and updating the UI state before we navigate.
            kotlinx.coroutines.delay(1000)
            onNavigateToResult(uiState.ingredientText)
            viewModel.resetAnalyzingState()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = androidx.compose.ui.graphics.Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Check Ingredients",
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = androidx.compose.ui.graphics.Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.White
                )
            )
        }
    ) { paddingValues ->
        InputScreenContent(
            uiState = uiState,
            paddingValues = paddingValues,
            onTextChange = { viewModel.onEvent(InputEvent.TextChanged(it)) },
            onClearText = { viewModel.onEvent(InputEvent.ClearText) }, // Integrated new event
            onCameraClick = {
                if (cameraPermission.status.isGranted) {
                    launchCamera(context) { uri ->
                        cameraImageUri = uri
                        cameraLauncher.launch(uri)
                    }
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            onGalleryClick = { galleryLauncher.launch("image/*") },
            onRemoveImage = { viewModel.onEvent(InputEvent.RemoveImage) },
            onToggleOptions = { viewModel.onEvent(InputEvent.ToggleImageOptions) },
            onAnalyzeClick = { viewModel.onEvent(InputEvent.AnalyzeClicked) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputScreenContent(
    uiState: InputUiState,
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    onTextChange: (String) -> Unit,
    onClearText: () -> Unit, // New parameter
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onRemoveImage: () -> Unit,
    onToggleOptions: () -> Unit,
    onAnalyzeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Upload or Type Ingredients",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = androidx.compose.ui.graphics.Color.Black
        )

        ImagePickerSection(
            selectedImageUri = uiState.selectedImageUri,
            showOptions = uiState.showImageOptions,
            onCameraClick = onCameraClick,
            onGalleryClick = onGalleryClick,
            onRemoveImage = onRemoveImage,
            onToggleOptions = onToggleOptions
        )

        Text(
            text = "OR",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        IngredientTextField(
            text = uiState.ingredientText,
            onTextChange = onTextChange,
            onClearText = onClearText, // Passed to component
            validationError = uiState.validationError,
            enabled = !uiState.isProcessingOcr && !uiState.isAnalyzing
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnalyzeButton(
            onClick = onAnalyzeClick,
            isAnalyzing = uiState.isAnalyzing,
            enabled = uiState.ingredientText.isNotBlank() && !uiState.isProcessingOcr
        )

        Spacer(modifier = Modifier.height(24.dp))

        OcrLoadingDialog(isVisible = uiState.isProcessingOcr)
    }
}

// ===== Previews Updated with ClearText Mock =====

@CompletePreview
@Composable
private fun InputScreenEmptyPreview() {
    FoodCheckAITheme {
        Scaffold { pv ->
            InputScreenContent(
                uiState = PreviewData.inputUiStateEmpty,
                paddingValues = pv,
                onTextChange = { },
                onClearText = { },
                onCameraClick = { },
                onGalleryClick = { },
                onRemoveImage = { },
                onToggleOptions = { },
                onAnalyzeClick = { }
            )
        }
    }
}

private fun launchCamera(context: android.content.Context, onUriCreated: (Uri) -> Unit) {
    val photoFile = File(context.cacheDir, "ingredient_${System.currentTimeMillis()}.jpg")
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        photoFile
    )
    onUriCreated(uri)
}