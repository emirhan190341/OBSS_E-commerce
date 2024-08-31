import axios from "axios";
import { useState } from "react";
import { useAuthStore } from "../store/authStore";
import { Link } from "react-router-dom";
import TransitionText from "../components/decorators/TransitionText";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuthStore();

  axios.defaults.withCredentials = true;

  const handleLogin = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);

    await login(formData);
  };

  return (
    <div className="flex min-h-screen items-center justify-center   py-12 px-4 sm:px-6 lg:px-8">
      <div className="w-full max-w-md space-y-8 bg-white p-10 rounded-xl shadow-lg">
        <div className="text-center">
          <img
            className="mx-auto h-20 w-auto"
            src="/src/imgs/logo.jpg"
            alt="Your Company"
          />
          <h2 className="mt-6 text-3xl font-extrabold text-gray-900">
            <span className="bg-gradient-to-r from-sky-500 to-slate-800 text-transparent bg-clip-text rounded-lg">
              Log In
            </span>
          </h2>
          <p className="mt-2 text-sm text-gray-600">
            Don't have an account?{" "}
            <TransitionText>
              <Link
                to="/signup"
                className="font-medium text-sky-500 hover:text-sky-700"
              >
                Sign Up
              </Link>
            </TransitionText>
          </p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleLogin}>
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
                autoComplete="current-password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-sky-500 focus:border-sky-500 sm:text-sm"
                placeholder="Password"
              />
            </div>
          </div>
          <div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-sky-500 hover:bg-sky-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-sky-500"
            >
              Log in
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

export default LoginPage;
