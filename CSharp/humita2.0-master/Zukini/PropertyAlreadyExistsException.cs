using System;

namespace Zukini
{
    public class PropertyAlreadyExistsException : Exception
    {
        public string Key { get; }

        public PropertyAlreadyExistsException(string key)
            : base($@"A property with the name {"name"} was already rememberd.")
        {
            if (key == null) throw new ArgumentNullException(nameof(key));
            Key = key;
        }
    }
}
