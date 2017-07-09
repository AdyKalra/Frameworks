using System.Diagnostics;

namespace Selenium.Core.Framework.Logger
{
    public class EventLogger
    {
        public static void Log(string message)
        {
            EventLog eventLog = new EventLog();
            eventLog.Source = "Selenium Log";
            eventLog.WriteEntry(message);
        }
    }
}
