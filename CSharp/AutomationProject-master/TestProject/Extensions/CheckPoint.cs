using NUnit.Framework;

namespace WilliamHill.Extensions
{
    public class CheckPoint
    {
        private static bool bPassReportingRequired = false;

        public static bool VerifyAreEqual(string strExp, string strActual, string customMessage)
        {
            bool bRetVal = strExp == strActual;
            if (bRetVal != true)
            {
                Reporter.ReportEvent(Reporter.EventType.Fail, string.Format("{0} Expected:- {1}, Actual:- {2}.", customMessage, strExp, strActual), "   ");
            }
            else if (bPassReportingRequired)
            {
                Reporter.ReportEvent(Reporter.EventType.Pass, string.Format("Expected:- {0}, Actual:- {1}.", strExp, strActual), "   ");
            }
            return bRetVal;
        }

        public static bool VerifyAreEqual(bool bExp, bool bActual, string customMessage)
        {
            bool bRetVal = bExp == bActual;
            if (bRetVal != true)
            {
                Reporter.ReportEvent(Reporter.EventType.Fail, string.Format("{0} Expected:- {1}, Actual:- {2}.", customMessage, bExp, bActual), "   ");
            }
            return bRetVal;
        }

        public static void AssertAreEqual(bool bExp, bool bActual, string customMessage)
        {
            bool bRetVal = bExp == bActual;
            if (bRetVal != true)
            {
                Reporter.ReportEvent(Reporter.EventType.Fail, customMessage);
                Assert.Fail();
            }
        }
    }
}