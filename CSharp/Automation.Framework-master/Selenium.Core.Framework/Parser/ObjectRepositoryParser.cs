using Data.Utilities;
using Selenium.Core.Framework.Utilities;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Xml.Linq;

namespace Selenium.Core.Framework.Parser
{
    public class ObjectRepositoryParser
    {
        private static IDictionary<string, XDocument> _xmlRepository;
        
        static ObjectRepositoryParser()
        {
            _xmlRepository = LoadAllXmls();
        }

        public static FindsByAttribute GetLocatorTypeAndValue(string page, string field)
        {
            try
            {
                var element = _xmlRepository[page].Descendants()
                              .First(x => (string)x.Attribute("name") == field);
                
                var how = element.Attribute("how").Value;
                var findBy = GetFindBy(how);
                var findByvalue = element.Attribute("using").Value;

                return new FindsByAttribute() {How = findBy, Using = findByvalue};
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        private static Dictionary<string, XDocument> LoadAllXmls()
        {
            try
            {
                var xmlPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory,
                ConfigurationManager.AppSettings["XmlFilesPath"]);

                var xmlDocuments = new Dictionary<string, XDocument>();

                var filesPath = Directory.GetFiles(xmlPath, "*.xml", SearchOption.AllDirectories);

                if (filesPath.Length == 0)
                {
                    throw new Exception("No Xml Files found");
                }

                foreach (var file in filesPath)
                {
                    xmlDocuments.Add(GetFileName(file), XmlReader.LoadXml(file));
                }

                return xmlDocuments;
            }

            catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }

        private static string GetFileName(string filePath)
        {
            var splitFilePath = filePath.Split('\\');
            
            if(splitFilePath.Length == 0)
            {
                throw new Exception("Unable to get the XMl File name");
            }

            return splitFilePath.Last().Replace(".xml", "");
        }

        private static How GetFindBy(string findBy)
        {
            switch(findBy.ToLower())
            {
                case "id":
                    return How.Id;

                case "xpath":
                    return How.Xpath;

                case "name":
                    return How.Name;

                case "cssselector":
                    return How.CssSelector;

                case "linktext":
                    return How.LinkText;

                case "partiallinktext":
                    return How.PartialLinkText;

                case "tagname":
                    return How.TagName;

                case "classname":
                    return How.ClassName;

                default:
                    throw new Exception(findBy + " is not a valid locator");
            }
        }
    }
}
