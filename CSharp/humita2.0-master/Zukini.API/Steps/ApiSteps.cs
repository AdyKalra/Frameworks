﻿using BoDi;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using Zukini.Steps;

namespace Zukini.API.Steps
{
    public abstract class ApiSteps : BaseSteps
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="ApiSteps"/> class.
        /// </summary>
        /// <param name="objectContainer">The object container.</param>
        protected ApiSteps(IObjectContainer objectContainer) :
            base(objectContainer)
        {
        }

        /// <summary>
        /// Performs a GET request to the specified API call and returns the data returned.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API to get.</param>
        /// <returns>Dictionary of key/value pairs containing the result data.</returns>
        protected IRestResponse<Dictionary<string, string>> Get(Uri baseUrl, string resource)
        {
            var restClient = new RestClient(baseUrl);
            var request = new RestRequest(resource, Method.GET);

            return restClient.Execute<Dictionary<string, string>>(request);
        }

        /// <summary>
        /// Helper method for posting to a Rest client and receiving a full response.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="postData"></param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected IRestResponse<Dictionary<string,string>> Post(Uri baseUrl, string resource, object postData)
        {
            if (postData == null)
            {
                throw new ArgumentNullException(nameof(postData));
            }

            // Setup rest client
            var restClient = new RestClient(baseUrl);

            // Setup rest request
            var request = new RestRequest(resource, Method.POST);
            request.AddJsonBody(postData);

            // Get response
            return restClient.Execute<Dictionary<string, string>>(request);
        }

        /// <summary>
        /// Helper method for making a put call to a Rest client and receiving a full response.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="putData">A JSON Serializable object to use as Put data.</param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected IRestResponse<Dictionary<string, string>> Put(Uri baseUrl, string resource, object putData)
        {
            if (putData == null)
            {
                throw new ArgumentNullException(nameof(putData));
            }

            // Setup rest client
            var restClient = new RestClient(baseUrl);

            // Setup rest request
            var request = new RestRequest(resource, Method.PUT);
            request.AddJsonBody(putData);

            // Get response
            return restClient.Execute<Dictionary<string, string>>(request);
        }

        /// <summary>
        /// Helper method for making a put call to a Rest client and receiving a full response.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="updateData">A JSON Serializable object to use as Put data.</param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected IRestResponse<Dictionary<string, string>> Update(Uri baseUrl, string resource, object updateData)
        {
            return Put(baseUrl, resource, updateData);
        }

        /// <summary>
        /// Helper method for making a patch call to a Rest client and receiving a full response.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="patchData">A JSON Serializable object to use as Put data.</param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected IRestResponse<Dictionary<string, string>> Patch(Uri baseUrl, string resource, object patchData)
        {
            if (patchData == null)
            {
                throw new ArgumentNullException(nameof(patchData));
            }

            // Setup rest client
            var restClient = new RestClient(baseUrl);

            // Setup rest request
            var request = new RestRequest(resource, Method.PATCH);
            request.AddJsonBody(patchData);

            // Get response
            return restClient.Execute<Dictionary<string, string>>(request);
        }

        /// <summary>
        /// Performs a delete operation for the specified ressource.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API to delete.</param>
        /// <returns>RestSharp response object resulting from the call.</returns>
        protected IRestResponse<Dictionary<string,string>> Delete(Uri baseUrl, string resource)
        {
            var restClient = new RestClient(baseUrl);
            var request = new RestRequest(resource, Method.DELETE);

            return restClient.Execute<Dictionary<string, string>>(request);
        }

        /// <summary>
        /// Helper method for posting to a Rest client and receiving a simple response
        /// in the form of a string dictionary. This allows simple access to members.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="postData"></param>
        /// <returns>Dictionary of key/value pairs containing the result data.</returns>
        protected Dictionary<string, string> SimplePost(Uri baseUrl, string resource, object postData)
        {
            return Post(baseUrl, resource, postData).Data;
        }

        /// <summary>
        /// Performs a GET request to the specified API call and returns the data returned.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API to get.</param>
        /// <returns>Dictionary of key/value pairs containing the result data.</returns>
        protected Dictionary<string, string> SimpleGet(Uri baseUrl, string resource)
        {
            return Get(baseUrl, resource).Data;
        }

        /// <summary>
        /// Helper method for making a simple patch call to a Rest client and receiving just the data returned.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="patchData">A JSON Serializable object to use as Patch data.</param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected Dictionary<string, string> SimplePatch(Uri baseUrl, string resource, object patchData)
        {
            return Patch(baseUrl, resource, patchData).Data;
        }

        /// <summary>
        /// Helper method for making a simple put call to a Rest client and receiving just the data returned.
        /// Return data is stored as a string dictionary for easy access.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource in the API (e.g. /posts).</param>
        /// <param name="putData">A JSON Serializable object to use as Put data.</param>
        /// <returns>RestResponse object (from RestSharp)</returns>
        protected Dictionary<string, string> SimplePut(Uri baseUrl, string resource, object putData)
        {
            return Patch(baseUrl, resource, putData).Data;
        }

        /// <summary>
        /// Performs a delete of the specified resource and returns the only the HttpResponseCode.
        /// </summary>
        /// <param name="baseUrl">The base URL of the API endpoint.</param>
        /// <param name="resource">The resource to delete.</param>
        /// <returns>HttpResponseCode as a result of the operation.</returns>
        protected HttpStatusCode SimpleDelete(Uri baseUrl, string resource)
        {
            return Delete(baseUrl, resource).StatusCode;
        }
        
    }
}
