import { useEffect, useState } from "react";
import RotatedText from "../components/decorators/RotatedText";
import UserCard from "../components/user/UserCard";
import { useUserStore } from "../store/userStore";
import UserForm from "../components/user/UserForm";

const UsersPage = () => {
  const { getUsers, users, getUsersSortedBy, isLastPage } = useUserStore();
  const [pageNumber, setPageNumber] = useState(0);
  const pageSize = 6;

  useEffect(() => {
    getUsers(pageNumber, pageSize);
  }, [getUsers, pageNumber]);

  const handleNextPage = () => {
    setPageNumber((prevPageNumber) => prevPageNumber + 1);
  };

  const handlePreviousPage = () => {
    if (pageNumber > 0) {
      setPageNumber((prevPageNumber) => prevPageNumber - 1);
    }
  };

  return (
    <div className="max-w-screen-xl mx-auto p-4 md:p-8">
      <h1 className="text-3xl font-extrabold text-gray-900 text-center mb-8">
        <RotatedText>USERS</RotatedText>
      </h1>

      <div className="flex justify-end  gap-4 mb-6">
        <button
          className="btn"
          onClick={() => document.getElementById("my_modal_2").showModal()}
        >
          ADD NEW USER
        </button>
      </div>

      <div className="border rounded-xl mb-10">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 m-2">
          {users.map((user) => (
            <UserCard key={user.id} user={user} />
          ))}
        </div>
      </div>

      <div className="flex justify-center items-center mt-8 gap-3">
        <button
          onClick={handlePreviousPage}
          disabled={pageNumber === 0}
          className="px-4 py-2 bg-gray-800 text-white rounded disabled:opacity-50"
        >
          Previous
        </button>

        <span className="text-gray-800 font-semibold">
          Page {pageNumber + 1}
        </span>

        <button
          onClick={handleNextPage}
          className="px-4 py-2 bg-gray-800 text-white rounded disabled:opacity-50"
          disabled={isLastPage}
        >
          Next
        </button>

        <dialog id="my_modal_2" className="modal">
          <div className="modal-box ">
            {" "}
            <div className="py-4">
              <UserForm />
            </div>
            <div className="modal-action flex justify-end mt-4">
              {" "}
              <button
                className="btn absolute bottom-2"
                onClick={() => document.getElementById("my_modal_2").close()}
              >
                Close
              </button>
            </div>
          </div>
        </dialog>
      </div>
    </div>
  );
};

export default UsersPage;
