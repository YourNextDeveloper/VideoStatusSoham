package com.example.videostatus.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.library.BuildConfig
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal

private val TAG = "Extentions"

fun Context.isNightMode() =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

fun Context.layoutInflater() = (this as Activity).layoutInflater

fun AppCompatEditText.hidePassword(isHide: Boolean) {
    transformationMethod =
        if (isHide) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    setSelection(length())
}

fun BigDecimal?.toDoubleNotNull() : Double{
    try {
        return this!!.toDouble()
    }catch (e : Exception){
        JLog.e(TAG,e.toString())
    }
    return 0.0
}

inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * Return trimmed text of EditText
 * */
fun EditText.getTrimText(): String = text.toString().trim()

/**
 * Return true If EditText is empty otherwise false
 * */
fun EditText.isEmpty(): Boolean = TextUtils.isEmpty(text.toString().trim())

inline fun EditText.afterTextChanged(crossinline listener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            listener(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

inline fun EditText.setOnRightDrawableClickListener(crossinline listener: () -> Unit) {
    setOnTouchListener(View.OnTouchListener { v, event ->
        // val DRAWABLE_LEFT = 0
        // val DRAWABLE_TOP = 1
        val drwableRight = 2
        // val DRAWABLE_BOTTOM = 3
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= right - compoundDrawables[drwableRight].bounds.width()) {
                listener()
                return@OnTouchListener true
            }
        }
        false
    })
}

inline fun EditText.setOnLeftDrawableClickListener(crossinline listener: () -> Unit) {
    setOnTouchListener(View.OnTouchListener { v, event ->
        // val DRAWABLE_LEFT = 0
        // val DRAWABLE_TOP = 1
        val drwableLeft = 0
        // val DRAWABLE_BOTTOM = 3
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= left - compoundDrawables[drwableLeft].bounds.width()) {
                listener()
                return@OnTouchListener true
            }
        }
        false
    })
}

/**
 * Enable/Disable EditText to editable
 * */
fun EditText.setEditable(enable: Boolean) {
    isFocusable = enable
    isFocusableInTouchMode = enable
    isClickable = enable
    isCursorVisible = enable
}

/*
* Make EditText Scrollable inside scrollview
* */
fun EditText.makeScrollableInScrollView() {
    setOnTouchListener(View.OnTouchListener { v, event ->
        if (hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return@OnTouchListener true
                }
            }
        }
        false
    })
}

/*
* Show Error at TextInputLayout
* */
fun TextInputLayout.showError(errorText: String) {
    if (errorText.isNotEmpty()) {
        isErrorEnabled = true
        error = errorText
    }
}

/*
* Hide error at TextInputLayout
* */
fun TextInputLayout.hideError() {
    isErrorEnabled = false
    error = null
}


/**
 * Check minimum length of EditText content
 * */
fun EditText.hasMinLength(minLength: Int): Boolean {
    return getTrimText().length >= minLength
}

/*
* Run Block of function on debug mode
* */
inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

/*
* Execute block if OS version is greater or equal Lolipop(21)
* */
inline fun lollipopAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

/*
* Execute block if OS version is greater than or equal Naugat(24)
* */
inline fun nougatAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        block()
    }
}

/**
 * Check internet connection.
 * */
inline fun Context.withNetwork(block: () -> Unit, blockError: () -> Unit) {
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let { it ->
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (netInfo.isConnected)
                block()
            else
                blockError()
        }
    }
}

/*
* Execute block into try...catch
* */
inline fun <T> justTry(tryBlock: () -> T) = try {
    tryBlock()
} catch (e: Throwable) {
    e.printStackTrace()
}

// Start new Activity functions

/*
* Start Activity FromOrderConfirm Activity
* */
inline fun <reified T : Any> Context.launchActivity(
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

/*
* Start Activity FromOrderConfirm Activity
* */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)
}

/*
* Start Activity FromOrderConfirm fragment
* */
inline fun <reified T : Any> Fragment.launchActivity(
    requestCode: Int = -1,
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this.context!!)
    intent.init()
    if (requestCode == -1)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)

}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * Return simple class noOfClothes
 * */
fun Any.getClassName(): String {
    return this::class.java.simpleName
}

/*
* Show Home button at ActionBar and set icon
* */
//fun ActionBar?.showHomeButton(show: Boolean, @DrawableRes icon: Int = R.drawable.icn_back) {
//    this?.setDisplayHomeAsUpEnabled(show)
//    this?.setDisplayShowHomeEnabled(show)
//    this?.setHomeAsUpIndicator(icon)
//
//}


fun Any.showVLog(tag: String, log: String) = JLog.v(tag, log)

fun Any.showELog(tag: String, log: String) = JLog.e(tag, log)

fun Any.showDLog(tag: String, log: String) = JLog.d(tag, log)

fun Any.showILog(tag: String, log: String) = JLog.i(tag, log)

fun Any.showWLog(tag: String, log: String) = JLog.w(tag, log)


fun Intent.getInt(key: String, defaultValue: Int = 0): Int {
    return extras?.getInt(key, defaultValue) ?: defaultValue
}

fun Intent.getString(key: String, defaultValue: String = ""): String {
    return extras?.getString(key, defaultValue) ?: defaultValue
}

/*
* Return activity main content view
* */
val Activity.contentView: View?
    get() = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)


/**
 * Hide/Show view with scale animation
 * */
fun View.setVisibilityWithScaleAnim(visibility: Int) {
    this.clearAnimation()
    this.visibility = View.VISIBLE
    val scale = if (visibility == View.GONE)
        0f
    else
        1f

    val scaleDown = ObjectAnimator.ofPropertyValuesHolder(this,
        PropertyValuesHolder.ofFloat("scaleX", scale),
        PropertyValuesHolder.ofFloat("scaleY", scale))
    scaleDown.duration = 300
    scaleDown.interpolator = DecelerateInterpolator()
    scaleDown.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            this@setVisibilityWithScaleAnim.visibility = visibility
        }
    })
    scaleDown.start()
}

fun Context.getAppVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Any.showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).apply { show() }
}

fun SpannableString.setClickableSpan(start: Int, end: Int, @ColorInt color: Int, block: (view: View?) -> Unit) {
    setSpan(object : ClickableSpan() {


//        override fun updateDrawState(ds: TextPaint?) {
//            ds?.isUnderlineText = false // set to false to remove underline
//        }

        override fun onClick(p0: View) {
            block(p0)
        }

    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    // Set Color Span
    setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

/*
* Toggle integer value, FromOrderConfirm 0->1 or 1->0
* */
fun Int.toggle() = if (this == 1) 0 else 1

/*
* Return true if view is visible otherwise return false
* */
fun View.isVisible() = visibility == View.VISIBLE

/*
* Set enabled/disable
* */
fun View.setEnabledWithAlpha(enabled: Boolean, disabledAlpha: Float = 0.5f) {
    isEnabled = enabled
    alpha = if (isEnabled) 1f else disabledAlpha
}


fun String?.nullSafe(defaultValue: String = ""): String {
    return this ?: defaultValue
}

fun Int?.nullSafe(defaultValue: Int = 0): Int {
    return this ?: defaultValue
}

fun Long?.nullSafeL(defaultValue: Long = 0L): Long {
    return this ?: defaultValue
}

fun Double?.nullSafe(defaultValue: Double = 0.0): Double {
    return this ?: defaultValue
}

fun Boolean?.nullSafe(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}

/*fun ImageView.loadCircleImage(imageUrl: String, @DrawableRes placeholder: Int = R.drawable.placeholder_user,
                              @DrawableRes errorPlaceholder: Int = R.drawable.placeholder_rounded_corner_grey) {
    GlideApp.with(context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(placeholder)
            .error(errorPlaceholder)
            .into(this)
}

fun ImageView.loadRoundedCornerImage(context: Context, url: String, roundedCorner: Int = context.resources.getDimensionPixelOffset(R.dimen.dp10),
                                     @DrawableRes placeholder: Int = R.drawable.placeholder_rounded_corner_grey,
                                     @DrawableRes errorPlaceholder: Int = R.drawable.placeholder_rounded_corner_grey) {
    GlideApp.with(context)
            .load(url)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(roundedCorner)))
            //.transition(withCrossFade())
            .placeholder(placeholder)
            .error(errorPlaceholder)
            .into(this)
}*/

/*
* Set enable/disable collapsingtoolbar scroll
* */
//fun CollapsingToolbarLayout.setCollapsable(isEnable: Boolean) {
//    val params = layoutParams as AppBarLayout.LayoutParams
//    params.scrollFlags = if (isEnable) AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
//    else
//        AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
//
//    layoutParams = params
//}

fun View?.visible() {
    if (this != null)
        this.visibility = View.VISIBLE
}

fun View?.hide() {
    if (this != null)
        this.visibility = View.GONE
}

@SuppressWarnings("deprecation")
fun String?.fromHtml(): Spanned {
    if (this == null)
        return SpannableString("")
    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
        // we are using this flag to give a consistent behaviour
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        return Html.fromHtml(this)
    }
}