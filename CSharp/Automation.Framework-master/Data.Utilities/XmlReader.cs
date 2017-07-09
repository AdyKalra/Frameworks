using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;

namespace Data.Utilities
{
    public class XmlReader
    {
        public static IEnumerable<XElement> GetDescendantNodes(string filePath, string xName)
        {
            try
            {
                var document = LoadXml(filePath);
                var xelement = document.Descendants().SingleOrDefault(x => x.Name == xName);

                return xelement.Descendants();
            }
            
            catch(Exception ex)
            {
                throw new Exception("No matching node found in the xml for :" + xName + "Exception Message: " + ex.Message);
            }
            
        }

        public static XElement GetNode(string filePath, string xname)
        {
            try
            {
                var document = LoadXml(filePath);
                var node = document.Descendants()
                              .SingleOrDefault(x => x.Name == xname);
                return node;
            }

            catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }

        public static XElement GetNodeMatchingAttribute(string filePath, string attribute, string attributevalue)
        {
            try
            {
                var document = LoadXml(filePath);
                var node = document.Descendants()
                              .First(x => (string)x.Attribute(attribute) == attributevalue);
                return node;
            }

            catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }

        public static XDocument LoadXml(string filePath)
        {
            try
            {
                if (File.Exists(filePath) || !string.IsNullOrEmpty(filePath))
                {
                    return XDocument.Load(filePath);
                }

                throw new Exception(string.Format("{0} is not a valid File Path", filePath));
            }
            
            catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }

        public static string GetNodeValue(string filePath, string nodeName)
        {
            try
            {
                var node = GetNode(filePath, nodeName);
                var nodevalue = node.Value;
                return nodevalue;
            }

            catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }
    }

}
