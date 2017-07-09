using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

namespace Data.Utilities
{
    public class SqlReader
    {
        #region Config Variables
        private static string _connectionString;
        private static string _dataSource;
        private static string _databaseName;
        private static string _userId;
        private static string _password;
        #endregion

        public SqlReader()
        {
            _dataSource = ConfigurationManager.AppSettings["Server"];
            _databaseName = string.Format("CALVI_CD_{0}_NODE_1", ConfigurationManager.AppSettings["Url"].Replace('-', '_'));
            _userId = ConfigurationManager.AppSettings["UserId"];
            _password = ConfigurationManager.AppSettings["UserPassword"];

            _connectionString = string.Format(
               "Data Source={0}; Initial catalog = {1}; User ID = {2}; Password = {3}", _dataSource, _databaseName,
               _userId, _password);
        }

        public int ExecuteQuery(string query)
        {
            try
            {
                SqlConnection connection;
                using (connection = new SqlConnection(_connectionString))
                {
                    var command = new SqlCommand
                    {
                        CommandText = query,
                        Connection = connection
                    };
                    connection.Open();

                    var result = command.ExecuteNonQuery();
                    connection.Close();
                    return result;
                }
            }

            catch (SqlException ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public DataTable FetchDataasDataTable(string query)
        {
            var dataTable = new DataTable();

            try
            {
                SqlConnection connection;
                using (connection = new SqlConnection(_connectionString))
                {
                    var command = new SqlCommand
                    {
                        CommandText = query,
                        Connection = connection
                    };
                    connection.Open();

                    var adapter = new SqlDataAdapter(command);
                    adapter.Fill(dataTable);
                    connection.Close();
                    adapter.Dispose();
                    return dataTable;
                }
            }

            catch (SqlException ex)
            {
                throw new Exception("Exception message: " + ex.Message);
            }

        }

        public object ExecuteScalar(string query)
        {
            try
            {
                SqlConnection connection;
                using (connection = new SqlConnection(_connectionString))
                {
                    var command = new SqlCommand
                    {
                        CommandText = query,
                        Connection = connection
                    };
                    connection.Open();

                    var result = command.ExecuteScalar();
                    connection.Close();
                    return result;
                }
            }

            catch (SqlException ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public void ExecuteStoredProcedure(string storedProcName, List<StoredProcParams> spParams)
        {
            try
            {
                SqlConnection connection;
                using (connection = new SqlConnection(_connectionString))
                {
                    var command = new SqlCommand(storedProcName, connection);
                    command.CommandType = CommandType.StoredProcedure;
                    
                    foreach(var parameter in spParams)
                    {
                        command.Parameters.Add(parameter.ParameterName, parameter.SqlDbType).Value = parameter.ParmeterValue;
                    }

                    connection.Open();

                    command.ExecuteNonQuery();
                    connection.Close();
                }
            }

            catch (SqlException ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public void ExecuteStoredProcedure(string storedProcName, Dictionary<string, string> spParamsWithValues)
        {
            try
            {
                SqlConnection connection;
                using (connection = new SqlConnection(_connectionString))
                {
                    var command = new SqlCommand(storedProcName, connection);
                    command.CommandType = CommandType.StoredProcedure;

                    foreach (var parameter in spParamsWithValues.Keys)
                    {
                        command.Parameters.AddWithValue(parameter, spParamsWithValues[parameter]);
                    }

                    connection.Open();

                    command.ExecuteNonQuery();
                    connection.Close();
                }
            }

            catch (SqlException ex)
            {
                throw new Exception(ex.Message);
            }

        }

    }

    public struct StoredProcParams
    {
        public string ParameterName;
        public SqlDbType SqlDbType;
        public string ParmeterValue;
    }
}
