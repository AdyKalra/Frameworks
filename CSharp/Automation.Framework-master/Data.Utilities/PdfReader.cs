using iTextSharp.text.pdf;
using iTextSharp.text.pdf.parser;
using System;
using System.Text;

namespace Data.Utilities
{
    public class PdfDataReader
    {
        public static string ExtractTextFromPdf(string filePath)
        {
            try
            {
                using (PdfReader reader = new PdfReader(filePath))
                {
                    StringBuilder text = new StringBuilder();

                    for (int i = 1; i <= reader.NumberOfPages; i++)
                    {
                        text.Append(PdfTextExtractor.GetTextFromPage(reader, i));
                    }

                    var fileContent = text.ToString();
                    return fileContent;
                }
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            } 
        }
    }
}
