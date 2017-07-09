using System;
using System.IO;
using System.Collections;

namespace WilliamHill.Extensions
{
    public class Reporter
    {
        #region Class fields

        private static bool _isStackTraceRequired;
        private static string _resultTemplatePath;
        private static StreamWriter _logger;
        private static int _xlStartRowNumber = 2;
        private static string _currentXlReport;
        private static string _currentTestName;
        private static string _testName;
        private static bool _isTestPassed = true;
        private static string _currentLogFile;

        #endregion Class fields

        public enum EventType
        {
            Fail = 0,
            Pass,
            Warning,
            Info,
            Error,
            Skip,
            Stacktrace,
            Start,
            End
        }

        private enum ReportColumn
        {
            SerialNumber = 1,
            TestScriptName,
            ExecutionResult
        }

        /// <summary>
        /// Report into stream writer.
        /// </summary>
        /// <param name="eventType">Message type(Fail,Pass,Warning,Iteration,Info,Error,StackTrace)</param>
        /// <param name="strMsg">Message string</param>
        public static void ReportEvent(EventType eventType, String strMsg, string MessagePrifix = "")
        {
            if (_logger != null)
            {
                switch (eventType)
                {
                    case EventType.Fail:
                        _logger.WriteLine("FAIL: {0}", strMsg);
                        break;

                    case EventType.Pass:
                        _logger.WriteLine("PASS: {0}", strMsg);
                        break;

                    case EventType.Warning:
                        _logger.WriteLine("WARNING: {0}", strMsg);
                        break;

                    case EventType.Start:
                        _logger.WriteLine("{0} Test Started\n", strMsg);
                        break;

                    case EventType.End:
                        _logger.WriteLine("\n{0} Test {1}", MessagePrifix, strMsg);
                        break;

                    case EventType.Info:
                        _logger.WriteLine("INFO: {0}", strMsg);
                        break;

                    case EventType.Error:
                        _logger.WriteLine("ERROR: {0}", strMsg);
                        break;

                    case EventType.Skip:
                        _logger.WriteLine("SKIP: {0}", strMsg);
                        break;

                    case EventType.Stacktrace:
                        if (_isStackTraceRequired)
                        {
                            _logger.WriteLine("STACKTRACE: {0}", strMsg);
                        }
                        break;
                }
            }
            else
            {
                switch (eventType)
                {
                    case EventType.Fail:
                        _logger.WriteLine("FAIL: {0}", strMsg);
                        break;

                    case EventType.Pass:
                        _logger.WriteLine("PASS: {0}", strMsg);
                        break;

                    case EventType.Warning:
                        _logger.WriteLine("WARNING: {0}", strMsg);
                        break;

                    case EventType.Start:
                        Console.WriteLine("{0} Test Started\n", MessagePrifix);
                        break;

                    case EventType.End:
                        Console.WriteLine("\n{0} Test {1}", MessagePrifix, strMsg);
                        break;

                    case EventType.Info:
                        _logger.WriteLine("INFO: {0}", strMsg);
                        break;

                    case EventType.Error:
                        _logger.WriteLine("ERROR: {0}", strMsg);
                        break;

                    case EventType.Skip:
                        _logger.WriteLine("SKIP: {0}", strMsg);
                        break;

                    case EventType.Stacktrace:
                        if (_isStackTraceRequired)
                        {
                            _logger.WriteLine("STACKTRACE: {0}", strMsg);
                        }
                        break;
                }
            }
        }
    }
}