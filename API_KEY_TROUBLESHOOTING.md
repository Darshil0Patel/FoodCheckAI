# Gemini API Key Troubleshooting Guide

## Your API Key Status
✅ API key is present in `local.properties`
✅ Format looks correct

## Steps to Fix the Issue

### 1. Clean and Rebuild Project
After fixing the build.gradle.kts, you MUST rebuild:

**In Android Studio:**
1. **Build** → **Clean Project**
2. Wait for it to finish
3. **Build** → **Rebuild Project**
4. This regenerates `BuildConfig` with your API key

### 2. Sync Gradle Files
- Click **File** → **Sync Project with Gradle Files**
- Wait for sync to complete

### 3. Verify BuildConfig is Generated
After rebuild, check if `BuildConfig.GEMINI_API_KEY` exists:
- Navigate to: `app/build/generated/source/buildConfig/debug/com/example/foodcheckai/BuildConfig.java`
- Look for: `public static final String GEMINI_API_KEY = "your_key";`

### 4. Run the App Again
- Click **Run** (green triangle)
- Select your device/emulator
- Test the ingredient analysis

## Common Issues & Solutions

### Issue 1: "Unresolved reference: BuildConfig"
**Solution:**
- Clean and Rebuild project
- Sync Gradle files
- Make sure `buildFeatures { buildConfig = true }` is in `build.gradle.kts`

### Issue 2: API Key is Empty String
**Solution:**
- Check `local.properties` format (no quotes, no spaces):
  ```properties
  GEMINI_API_KEY=AIzaSy...your_key_here
  ```
- NOT like this: ~~`GEMINI_API_KEY="AIzaSy..."`~~
- NOT like this: ~~`GEMINI_API_KEY = AIzaSy...`~~

### Issue 3: "API key not valid"
**Solution:**
- Verify your API key at: https://makersuite.google.com/app/apikey
- Make sure it's enabled for Generative Language API
- Try creating a new API key if needed

### Issue 4: Network/API Errors at Runtime
**Possible causes:**
- No internet connection
- API quota exceeded (Gemini has daily limits)
- API key not activated yet (may take a few minutes)

## Debugging Steps

### Check if API Key is Loading
Add this temporarily to `GeminiApiHelper.kt` constructor:

```kotlin
init {
    println("🔑 API Key loaded: ${BuildConfig.GEMINI_API_KEY.take(10)}...")
}
```

This will print first 10 characters of your key in Logcat.

### Check API Response
Look for error messages in **Logcat**:
- Filter by: `FoodCheckAI` or `Gemini`
- Look for exception messages

## Verify Your local.properties Format

Should look exactly like this:
```properties
sdk.dir=C\:\\Users\\...\\Android\\Sdk
GEMINI_API_KEY=AIzaS...
```

**Important:**
- ✅ No quotes around the key
- ✅ No spaces around the `=`
- ✅ Key on its own line
- ✅ No comments on the same line

## Test the API Key Separately

You can test if your API key works using this simple test:

1. Go to: https://aistudio.google.com/
2. Sign in with the same Google account
3. Try generating content
4. If it works there, your key is valid

## Quick Checklist

- [ ] Fixed `build.gradle.kts` (now using `java.util.Properties`)
- [ ] API key is in `local.properties` (no quotes)
- [ ] Cleaned project (**Build** → **Clean Project**)
- [ ] Rebuilt project (**Build** → **Rebuild Project**)
- [ ] Synced Gradle files
- [ ] Internet connection is active
- [ ] API key is valid (check Google AI Studio)

## If Still Not Working

### Check Logcat for Specific Error
1. Open **Logcat** in Android Studio
2. Clear the log
3. Run the app
4. Try to analyze ingredients
5. Look for error messages (they'll be in red)
6. Share the error message for specific help

### Common Error Messages

**"Failed to analyze ingredients: 401 Unauthorized"**
- API key is invalid or not activated
- Generate a new key

**"Failed to analyze ingredients: 403 Forbidden"**
- API not enabled for your project
- Go to Google Cloud Console and enable Generative Language API

**"Failed to analyze ingredients: 429 Too Many Requests"**
- You've exceeded the free quota
- Wait 24 hours or upgrade to paid plan

**"Empty response from AI"**
- API call succeeded but returned nothing
- Might be a model issue, retry

## Need More Help?

If you're still stuck, provide:
1. The exact error message from Logcat
2. Screenshot of the error screen
3. First 10 characters of your API key (for verification)

The API should work now after the rebuild! 🚀
