package vn.vietmap.vietmapkmmsdktest


actual object Platform {
    actual val isAndroid: Boolean
        get() = false
    actual val isIos: Boolean
        get() = true
    actual val isDesktop: Boolean
        get() = false
    actual val isWeb: Boolean
        get() = false
}