import React, { useState } from "react";
import axios from "axios";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { useProductStore } from "../../store/productStore";

const ProductForm = ({ sellerId }) => {
  const { addNewProduct } = useProductStore();
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
      const formattedData = {
        ...data,
        categories: data.categories.split(",").map((cat) => cat.trim()),
      };

      const formData = new FormData();
      formData.append(
        "request",
        new Blob([JSON.stringify(formattedData)], { type: "application/json" })
      );
      if (file) {
        formData.append("file", file);
      }

      const response = await axios.post(
        `http://localhost:8080/api/v1/products/seller/${sellerId}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      toast.success("Product created successfully!");
      console.log(response.data);
      addNewProduct(response.data.response);
      reset();
      setFile(null);
    } catch (error) {
      toast.error("Failed to create product. Please try again later.");
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-screen-md mx-auto p-4 md:p-8 bg-white shadow-lg rounded-lg">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-900">
        Create Product
      </h1>
      <form onSubmit={handleSubmit(onSubmit)} encType="multipart/form-data">
        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="name"
          >
            Product Name
          </label>
          <input
            type="text"
            id="name"
            {...register("name", { required: true })}
            className={`w-full p-2 border ${
              errors.name ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.name && (
            <p className="text-red-500 text-sm">Product name is required.</p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="description"
          >
            Description
          </label>
          <textarea
            id="description"
            {...register("description", { required: true })}
            className={`w-full p-2 border ${
              errors.description ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.description && (
            <p className="text-red-500 text-sm">Description is required.</p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="price"
          >
            Price
          </label>
          <input
            type="number"
            id="price"
            {...register("price", { required: true })}
            className={`w-full p-2 border ${
              errors.price ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.price && (
            <p className="text-red-500 text-sm">Price is required.</p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="quantity"
          >
            Quantity
          </label>
          <input
            type="number"
            id="quantity"
            {...register("quantity", { required: true })}
            className={`w-full p-2 border ${
              errors.quantity ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.quantity && (
            <p className="text-red-500 text-sm">Quantity is required.</p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="categories"
          >
            Categories (comma-separated)
          </label>
          <input
            type="text"
            id="categories"
            {...register("categories", { required: true })}
            className={`w-full p-2 border ${
              errors.categories ? "border-red-500" : "border-gray-300"
            } rounded`}
          />
          {errors.categories && (
            <p className="text-red-500 text-sm">Categories are required.</p>
          )}
        </div>

        <div className="mb-4">
          <label
            className="block text-gray-700 font-semibold mb-2"
            htmlFor="file"
          >
            Upload Image
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
          {isLoading ? "Creating..." : "Create Product"}
        </button>
      </form>
    </div>
  );
};

export default ProductForm;
