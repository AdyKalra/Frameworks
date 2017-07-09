using System;
using System.Data;
using System.Data.OleDb;

namespace Data.Utilities
{
    public class ExcelReader
    {
        public DataTable FetchData(string filePath, string sheetname)
        {
            if (string.IsNullOrEmpty(sheetname))
                sheetname = "Sheet1";

            var query = String.Format("SELECT * FROM [{0}$]", sheetname);

            var connectionString = "Provider=Microsoft.ACE.OLEDB.12.0;" +
                             "Data Source=" + filePath +
                             "Extended Properties='Excel 12.0;" +
                             "HDR=Yes;IMEX=1'";

            var myDataSet = new DataSet();

            try
            {
                var dataAdapter = new OleDbDataAdapter(query, connectionString);
                dataAdapter.Fill(myDataSet);
                var dataTable = myDataSet.Tables[0];
                return dataTable;
            }

            catch (OleDbException ex)
            {
                throw new Exception("Error loading file:" + ex.Message);
            }
        }
    }
}
