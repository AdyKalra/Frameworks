using System;

namespace Zukini
{
    public class PropertyNotFoundException : Exception
    {
        public PropertyNotFoundException(string propertyName)
            : base($"Property named {propertyName} was not found, are you sure it was remembered?")
        {
        }
    }
}
