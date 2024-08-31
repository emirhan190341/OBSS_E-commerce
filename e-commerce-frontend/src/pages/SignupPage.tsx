import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import TransitionText from "../components/decorators/TransitionText";

const SignupPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/users/save",
        {
          username,
          password,
          name,
          surname,
          email,
          phoneNumber,
          address,
        }
      );

      if (response.status === 201) {
        console.log("Signup successful");
        navigate("/login");
      }
    } catch (error) {
      setError("Signup failed. Please check your details and try again.");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center  py-12 px-4 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-8 bg-white p-10 rounded-xl shadow-lg">
        <div className="text-center">
          <img
            className="mx-auto h-20 w-auto"
            src="/src/imgs/logo.jpg"
            alt="Your Company"
          />
          <h2 className="mt-6 text-3xl font-extrabold text-gray-900">
            <span className="bg-gradient-to-r from-sky-500 to-slate-800 text-transparent bg-clip-text rounded-lg">
              Sign Up
            </span>
          </h2>
          <p className="mt-2 text-sm text-gray-600">
            Already have an account?{" "}
            <TransitionText>
              <Link
                to="/login"
                className="font-medium text-sky-500 hover:text-sky-700"
              >
                Login
              </Link>
            </TransitionText>
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit} method="POST">
          <div className="rounded-md shadow-sm space-y-4">
            <div>
              <label htmlFor="username" className="sr-only">
                Username
              </label>
              <input
                id="username"
                name="username"
                type="text"
                autoComplete="username"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Username"
                minLength={5}
                maxLength={15}
              />
            </div>
            <div>
              <label htmlFor="password" className="sr-only">
                Password
              </label>
              <input
                id="password"
                name="password"
                type="password"
                autoComplete="new-password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Password"
                minLength={8}
                maxLength={20}
              />
            </div>
            <div>
              <label htmlFor="email" className="sr-only">
                Email
              </label>
              <input
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Email"
                minLength={5}
                maxLength={50}
              />
            </div>
            <div>
              <label htmlFor="name" className="sr-only">
                Name
              </label>
              <input
                id="name"
                name="name"
                type="text"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Name"
                minLength={5}
                maxLength={20}
              />
            </div>
            <div>
              <label htmlFor="surname" className="sr-only">
                Surname
              </label>
              <input
                id="surname"
                name="surname"
                type="text"
                required
                value={surname}
                onChange={(e) => setSurname(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Surname"
                minLength={5}
                maxLength={20}
              />
            </div>

            <div>
              <label htmlFor="phoneNumber" className="sr-only">
                Phone Number
              </label>
              <input
                id="phoneNumber"
                name="phoneNumber"
                type="text"
                required
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Phone Number"
                minLength={10}
                maxLength={15}
              />
            </div>
            <div>
              <label htmlFor="address" className="sr-only">
                Address
              </label>
              <input
                id="address"
                name="address"
                type="text"
                required
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Address"
                minLength={10}
                maxLength={100}
              />
            </div>
          </div>
          <div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-sky-500 hover:bg-sky-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-sky-500"
            >
              Sign up
            </button>
          </div>
          {error && (
            <div className="text-red-600 text-center mt-4">{error}</div>
          )}
        </form>
      </div>
    </div>
  );
};

export default SignupPage;
