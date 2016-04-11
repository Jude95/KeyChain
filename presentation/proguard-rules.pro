-keep public class * extends android.view.View

-keep class butterknife.** { *; }
-keep class afollestad.** { *; }
-keep class zhanghai.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
 @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
@butterknife.* <methods>;
}

-dontwarn java.lang.invoke.*