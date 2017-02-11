/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aguesoftguar.medalarm.util;

import com.aguesoftguar.medalarm.BuildConfig;
import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * This utility class is the main entrance to print log with Android Log class. Our application
 * should always use this class to print logs.
 */
public class Log {

   /**
    * Tag used together with the log tag in order to identify quickly all the logs of the app
    */
   private static final String LOG_TAG = "MA_";

   /**
    * Basic log format using for android logcat: "[METHOD_NAME] LOG_MESSAGE"
    */
   private static final String LOG_MESSAGE_FORMAT = "[%s] %s";

   /**
    * Basic log format using for {@link FirebaseCrash}: "LOG_LEVEL/TAG: LOG MESSAGE" Exception
    * message is logged if needed
    */
   private static final String FIREBASE_LOG_MESSAGE_FORMAT = "%s/%s: %s";

   /**
    * Variables used to know what log priority levels should be displayed
    */
   private static final boolean LOG_E = BuildConfig.LOG_E;
   private static final boolean LOG_W = BuildConfig.LOG_W;
   private static final boolean LOG_I = BuildConfig.LOG_I;
   private static final boolean LOG_D = BuildConfig.LOG_D;
   private static final boolean LOG_V = BuildConfig.LOG_V;

   /**
    * Send a {@link android.util.Log#VERBOSE} log message.
    *
    * @param tag     Used to identify the source of a log message. It usually identifies the class
    *                or activity where the log call occurs.
    * @param message The message to be logged.
    */
   public static void v(String tag, String message) {
      if (LOG_V) {
         printLog(android.util.Log.VERBOSE, tag, message, null);
      }
   }

   /**
    * Send a {@link android.util.Log#VERBOSE} log message and log the exception.
    *
    * @param tag       Used to identify the source of a log message. It usually identifies the
    *                  class or activity where the log call occurs.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    */
   public static void v(String tag, String message, Throwable throwable) {
      if (LOG_V) {
         printLog(android.util.Log.VERBOSE, tag, message, throwable);
      }
   }

   /**
    * Send a {@link android.util.Log#DEBUG} log message.
    *
    * @param tag     Used to identify the source of a log message. It usually identifies the class
    *                or activity where the log call occurs.
    * @param message The message to be logged.
    */
   public static void d(String tag, String message) {
      if (LOG_D) {
         printLog(android.util.Log.DEBUG, tag, message, null);
      }
   }

   /**
    * Send a {@link android.util.Log#DEBUG} log message and log the exception.
    *
    * @param tag       Used to identify the source of a log message. It usually identifies the
    *                  class or activity where the log call occurs.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    */
   public static void d(String tag, String message, Throwable throwable) {
      if (LOG_D) {
         printLog(android.util.Log.DEBUG, tag, message, throwable);
      }
   }

   /**
    * Send a {@link android.util.Log#INFO} log message.
    *
    * @param tag     Used to identify the source of a log message. It usually identifies the class
    *                or activity where the log call occurs.
    * @param message The message to be logged.
    */
   public static void i(String tag, String message) {
      if (LOG_I) {
         printLog(android.util.Log.INFO, tag, message, null);
      }
   }

   /**
    * Send a {@link android.util.Log#INFO} log message and log the exception.
    *
    * @param tag       Used to identify the source of a log message. It usually identifies the
    *                  class or activity where the log call occurs.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    */
   public static void i(String tag, String message, Throwable throwable) {
      if (LOG_I) {
         printLog(android.util.Log.INFO, tag, message, throwable);
      }
   }

   /**
    * Send a {@link android.util.Log#WARN} log message.
    *
    * @param tag     Used to identify the source of a log message. It usually identifies the class
    *                or activity where the log call occurs.
    * @param message The message to be logged.
    */
   public static void w(String tag, String message) {
      if (LOG_W) {
         printLog(android.util.Log.WARN, tag, message, null);
      }
   }

   /**
    * Send a {@link android.util.Log#WARN} log message and log the exception.
    *
    * @param tag       Used to identify the source of a log message. It usually identifies the
    *                  class or activity where the log call occurs.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    */
   public static void w(String tag, String message, Throwable throwable) {
      if (LOG_W) {
         printLog(android.util.Log.WARN, tag, message, throwable);
      }
   }

   /**
    * Send a {@link android.util.Log#ERROR} log message.
    *
    * @param tag     Used to identify the source of a log message. It usually identifies the class
    *                or activity where the log call occurs.
    * @param message The message to be logged.
    */
   public static void e(String tag, String message) {
      if (LOG_E) {
         printLog(android.util.Log.ERROR, tag, message, null);
      }
   }

   /**
    * Send a {@link android.util.Log#ERROR} log message and log the exception.
    *
    * @param tag       Used to identify the source of a log message. It usually identifies the
    *                  class or activity where the log call occurs.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    */
   public static void e(String tag, String message, Throwable throwable) {
      if (LOG_E) {
         printLog(android.util.Log.ERROR, tag, message, throwable);
      }
   }

   /**
    * Print log in android logcat using {@link Timber} library and send the log message to the
    * Firebase crash reporting tool.
    *
    * @param priority  Log priority, corresponding to {@link android.util.Log} priorities.
    * @param tag       Class tag. Typically is the name of the class that emits the log.
    * @param message   The message to be logged.
    * @param throwable An exception to log (can be null).
    * @see {@link FirebaseCrash}
    */
   private static void printLog(int priority, String tag, String message, Throwable throwable) {

      //Build the log tag using the app tag (see TAG variable) and the class tag
      String logTag = LOG_TAG + tag;

      //Build the log message including the name of the method that calls Log method
      String logMessage = String.format(LOG_MESSAGE_FORMAT, getMethodName(5), message);

      //Only send custom logs to Firebase for error, warning and info messages
      switch (priority) {
         case android.util.Log.VERBOSE:
            Timber.tag(logTag).v(throwable, logMessage);
            break;
         case android.util.Log.DEBUG:
            Timber.tag(logTag).d(throwable, logMessage);
            break;
         case android.util.Log.INFO:
            Timber.tag(logTag).i(throwable, logMessage);
            FirebaseCrash.log(getCrashReporterLogMessage("I", logTag, logMessage, throwable));
            break;
         case android.util.Log.WARN:
            Timber.tag(logTag).w(throwable, logMessage);
            FirebaseCrash.log(getCrashReporterLogMessage("W", logTag, logMessage, throwable));
            break;
         case android.util.Log.ERROR:
            Timber.tag(logTag).e(throwable, logMessage);
            FirebaseCrash.log(getCrashReporterLogMessage("E", logTag, logMessage, throwable));
            break;
      }
   }

   /**
    * Returns a {@link String} suitable for crash reporting tools
    *
    * @param priorityLevel Log level.
    * @param tag           Log tag.
    * @param msg           Log message.
    * @param throwable     Log exception (can be null).
    * @return A {@link String} suitable for crash reporting.
    */
   private static String getCrashReporterLogMessage(String priorityLevel, String tag, String msg, Throwable throwable) {
      String logString = String.format(FIREBASE_LOG_MESSAGE_FORMAT, priorityLevel, tag, msg);
      if (throwable != null) {
         logString = String.format("%s\n%s", logString, getStackTraceString(throwable));
      }
      return logString;
   }

   /**
    * Returns a stack trace string from a given {@link Throwable}
    *
    * @param throwable A {@link Throwable}
    * @return the {@link Throwable}'s stack trace in string format
    */
   private static String getStackTraceString(Throwable throwable) {
      return android.util.Log.getStackTraceString(throwable);
   }

   /**
    * Util method that calls {@link Log#reportCrash(Throwable, String, String)} with the current
    * method name.
    *
    * @param throwable Throwable object. It is usually an {@link Exception} object.
    * @param tag       Log tag of the class that calls to this method.
    */
   public static void reportCrash(Throwable throwable, String tag) {
      reportCrash(throwable, tag, getMethodName(throwable));
   }

   /**
    * Util method used to report a exception in the camera execution. An error log is shown and an
    * exception stack trace is printed.
    *
    * @param throwable  Throwable object. It is usually an {@link Exception} object.
    * @param tag        Log tag of the class that calls to this method.
    * @param methodName Name of the method that calls to this method.
    */
   private static void reportCrash(Throwable throwable, String tag, String methodName) {

      if (methodName == null) {
         methodName = getMethodName(throwable);
      }

      Log.e(tag, "[" + methodName + "] ERROR: " + throwable);
      throwable.printStackTrace();
      FirebaseCrash.report(throwable);
   }

   /**
    * Get the name of the method that throws an exception using the {@link Throwable} stack trace.
    *
    * @param throwable Throwable object. It is usually an {@link Exception} object.
    * @return Method name which throws the exception.
    */
   private static String getMethodName(Throwable throwable) {
      StackTraceElement[] stacktrace = throwable.getStackTrace();
      StackTraceElement stackTraceElement = stacktrace[0];
      return stackTraceElement.getMethodName();
   }

   /**
    * Get the method name for a depth in call stack.
    *
    * @param depth Depth in the call stack (0 means current method, 1 means call method, ...)
    * @return Method name
    */
   private static String getMethodName(final int depth) {
      final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
      return ste[depth].getMethodName();
   }
}
