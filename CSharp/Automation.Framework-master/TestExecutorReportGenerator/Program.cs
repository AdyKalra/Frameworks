using System;
using System.Configuration;
using System.Diagnostics;
using System.IO;

namespace TestExecutorReportGenerator
{
    class Program
    {
        static void Main(string[] args)
        {
            var rootPath = AppDomain.CurrentDomain.BaseDirectory + @"..\..\..\";

            var nunitConsolepath = Path.Combine(rootPath + ConfigurationManager.AppSettings["NUnitConsolePath"]);
            var testsDllpath = Path.Combine(rootPath + ConfigurationManager.AppSettings["TestsDllPath"]);
            var xmlResultsPath = Path.Combine(rootPath + ConfigurationManager.AppSettings["XmlResultsPath"]);
            var picklesexePath = Path.Combine(rootPath + ConfigurationManager.AppSettings["PicklesExepath"]);
            var featureFilespath = Path.Combine(rootPath + ConfigurationManager.AppSettings["FeatureFilesPath"]);
            var reportsPath = Path.Combine(rootPath + ConfigurationManager.AppSettings["ReportOutputpath"]);
            var testCategory = ConfigurationManager.AppSettings["TestCategory"];

            if (!Directory.Exists(reportsPath))
            {
                Directory.CreateDirectory(reportsPath);
            }

            var test = string.Format("{0} {1} {2} {3} {4} {5} {6}",
                nunitConsolepath, testsDllpath, testCategory, xmlResultsPath, picklesexePath,
                featureFilespath, reportsPath);

            Process proc = new Process();
            proc.StartInfo.FileName = Path.Combine(rootPath + ConfigurationManager.AppSettings["BatFilePath"]);
            proc.StartInfo.Arguments = string.Format("\"{0}\" \"{1}\" {2} \"{3}\" \"{4}\" \"{5}\" \"{6}\"",
                nunitConsolepath, testsDllpath, testCategory, xmlResultsPath, picklesexePath,
                featureFilespath, reportsPath);
            proc.Start();
        }
    }
}
