import React, { useState } from "react";
import axios from "axios";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { useSellerStore } from "../../store/sellerStore";

const SellerForm = () => {
  const { addNewSeller } = useSellerStore();
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();
  const [file, setFile] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const onFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const onSubmit = async (data) => {
    setIsLoading(true);

    try {
      const formData = new FormData();
      formData.append(
        "request",
        new Blob([JSON.stringify(data)], { type: "application/json" })
      );
      if (file) {
        formData.append("file", file);
      }

      const response = await axios.post(
        "http://localhost:8080/api/v1/sellers",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      toast.success("Seller created successfully!");
      console.log(response.data);
      addNewSeller(response.data.response);
      reset();
      setFile(null);
    } catch (error) {
      toast.error("Failed to create seller. Please try again later.");
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-screen-md mx-auto p-4 md:p-8 bg-white shadow-lg rounded-lg">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-900">
        Create Seller
      </h1>
      <form onSubmit={handleSubmit(onSubmit)} encType="multipart/form-data">
        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="companyName"
          >
            Company Name
          </label>
          <input
            type="text"
            id="companyName"
            {...register("companyName", {
              required: true,
              minLength: 5,
              maxLength: 15,
            })}
            className={`w-full p-2 border ${
              errors.companyName ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.companyName && (
            <p className="text-red-500 text-sm">
              Company name must be between 5 and 15 characters.
            </p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="contactNumber"
          >
            Contact Number
          </label>
          <input
            type="text"
            id="contactNumber"
            {...register("contactNumber", {
              required: true,
              minLength: 10,
              maxLength: 15,
            })}
            className={`w-full p-2 border ${
              errors.contactNumber ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.contactNumber && (
            <p className="text-red-500 text-sm">
              Contact number must be between 10 and 15 characters.
            </p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="email"
          >
            Email
          </label>
          <input
            type="email"
            id="email"
            {...register("email", {
              required: true,
              minLength: 5,
              maxLength: 50,
            })}
            className={`w-full p-2 border ${
              errors.email ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.email && (
            <p className="text-red-500 text-sm">
              Email must be between 5 and 50 characters.
            </p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="websiteUrl"
          >
            Website URL
          </label>
          <input
            type="text"
            id="websiteUrl"
            {...register("websiteUrl")}
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="address"
          >
            Address
          </label>
          <input
            type="text"
            id="address"
            {...register("address")}
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="rating"
          >
            Rating
          </label>
          <input
            type="number"
            step="0.1"
            id="rating"
            {...register("rating")}
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700 font-semibold mb-2">
            Verified Seller
          </label>
          <input
            type="checkbox"
            id="isVerified"
            {...register("isVerified")}
            className="h-4 w-4"
          />
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="file"
          >
            Upload Logo
          </label>
          <input
            type="file"
            id="file"
            onChange={onFileChange}
            className="w-full"
          />
        </div>

        <button
          type="submit"
          className={`w-full p-2 bg-blue-600 text-white font-semibold rounded ${
            isLoading ? "opacity-50 cursor-not-allowed" : ""
          }`}
          disabled={isLoading}
        >
          {isLoading ? "Creating..." : "Create Seller"}
        </button>
      </form>
    </div>
  );
};

export default SellerForm;
