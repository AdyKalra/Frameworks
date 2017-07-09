using Selenium.Core.Framework.Parser;
using Selenium.Core.Framework.Driver;
using Selenium.Core.Framework.Utilities;
using System;
using System.Linq;
using System.Reflection;
using Selenium.Core.Framework.UiControls;

namespace Selenium.Core.Framework.PageInitializers
{
    public class PageInitializer
    {
        private static UiDriver _driver;
        private static BasePage _page;

        public static void Initialize(BasePage page, UiDriver driver)
        {
            _driver = driver;
            _page = page;

            var fields = page.GetType().GetFields(BindingFlags.NonPublic | BindingFlags.Instance);

            if (fields.Length == 0)
                return;

            foreach(var field in fields)
            {                
                if (Attribute.IsDefined(field, typeof(FindsByAttribute)))
                {
                    if (field.FieldType.Name.Contains("WebElements"))
                    {
                        InitializeWebElements(field);
                    }

                    else
                    {
                        InitializeField(field);
                    }
                    
                }
            }
        }

        private static void InitializeField(FieldInfo fieldtobeinitialized)
        {
            try
            {
                var attribute = (FindsByAttribute)
                Attribute.GetCustomAttribute
                (fieldtobeinitialized, typeof(FindsByAttribute));

                attribute = GetFindsByValue(fieldtobeinitialized,attribute);

                if (attribute.parameters != null)
                {
                    attribute.Using = string.Format(attribute.Using, attribute.parameters);
                }

                string fieldname = fieldtobeinitialized.FieldType.Name;
                var fieldtype = Assembly.GetExecutingAssembly().GetTypes().First(x => x.Name == fieldname);
                var fieldObject = Activator.CreateInstance(fieldtype, _driver, attribute.How, attribute.Using);
                fieldtobeinitialized.SetValue(_page, fieldObject);
            }

            catch(Exception ex)
            {
                throw new Exception("Exception during initializing field. Exception Message:" + ex.Message);
            }
            
        }

        private static void InitializeWebElements(FieldInfo fieldtobeinitialized)
        {
            try
            {
                var attribute = (FindsByAttribute)
                Attribute.GetCustomAttribute
                (fieldtobeinitialized, typeof(FindsByAttribute));

                attribute = GetFindsByValue(fieldtobeinitialized, attribute);

                if (attribute.parameters != null)
                {
                    attribute.Using = string.Format(attribute.Using, attribute.parameters);
                }

                var genericTypeName = fieldtobeinitialized.FieldType.GetGenericArguments()[0].Name;
                var genericType = Assembly.GetExecutingAssembly().GetTypes().First(x => x.Name == genericTypeName);

                var type = typeof(WebElements<>);
                type = type.MakeGenericType(genericType);
                var typeObject = Activator.CreateInstance(type, _driver, attribute.How, attribute.Using);

                fieldtobeinitialized.SetValue(_page, typeObject);
            }

            catch(Exception ex)
            {
                throw new Exception("Exception while Initializing Collection Field. Exception Message: " + ex.Message);
            }
            
        }

        private static FindsByAttribute GetFindsByValue(FieldInfo field, FindsByAttribute attribute)
        {
            if (attribute.Using != null)
                return attribute;

            string xmlPage;
            string xmlField;

            if (!string.IsNullOrEmpty(attribute.Page) && !string.IsNullOrEmpty(attribute.Field))
            {
                xmlPage = attribute.Page;
                xmlField = attribute.Field;
            }
            
            else
            {
                xmlPage = _page.GetType().Name;
                xmlField = field.Name;
            }

            attribute = ObjectRepositoryParser.GetLocatorTypeAndValue(xmlPage, xmlField);
            return attribute;
        } 
    }
}
