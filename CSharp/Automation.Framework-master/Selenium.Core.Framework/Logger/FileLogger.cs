using System;
using System.Configuration;
using System.IO;

namespace Selenium.Core.Framework.Logger
{
    public class FileLogger
    {
        private static string _filePath;
        private static string _filename;

        static FileLogger()
        {
            _filePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory + 
                ConfigurationManager.AppSettings["LogFilePath"]);
            _filename = Path.Combine(_filePath + @"\Log.txt");
            CreateFile();
        }

        public static void Log(string message)
        {
            try
            {
                using (StreamWriter streamWriter = File.AppendText(_filename))
                {
                    streamWriter.WriteLine(message);
                    streamWriter.Close();
                }
            }

            catch(Exception ex)
            {
                throw new Exception("Exception while logging. Exception message:" + ex.Message);
            }
            
        }

        private static void CreateFile()
        {
            try
            {
                if (!Directory.Exists(_filePath))
                {
                    Directory.CreateDirectory(_filePath);
                }

                if (File.Exists(_filePath))
                {
                    File.Delete(_filePath);
                }

                // Create the file.
                FileStream fs = File.Create(_filename);
                fs.Close();
            }

            catch(Exception ex)
            {
                throw new Exception("Exception Creating Log File. Exception Message: " + ex.Message
                    );
            }
        }
    }
}
