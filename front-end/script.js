const API_URL = "http://localhost:8080/api"; // Adjust this to match your backend

// Switch between Login and Register forms
function showRegister() {
    document.getElementById("login-form").style.display = "none";
    document.getElementById("register-form").style.display = "block";
}

function showLogin() {
    document.getElementById("register-form").style.display = "none";
    document.getElementById("login-form").style.display = "block";
}

// Register New User
async function register() {
    const username = document.getElementById("register-username").value;
    const password = document.getElementById("register-password").value;

    try {
        const response = await fetch(`${API_URL}/auth/signup`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            alert("Registration successful! Please log in.");
            showLogin();
        } else {
            alert("Registration failed!");
        }
    } catch (error) {
        alert("An error occurred during registration.");
    }
}

// Login and Store JWT Token
async function login() {
    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem("jwt", data.token);
            window.location.href = "dashboard.html";
        } else {
            alert("Login failed!");
        }
    } catch (error) {
        alert("An error occurred during login.");
    }
}

// Logout
function logout() {
    localStorage.removeItem("jwt");
    window.location.href = "index.html";
}

// Fetch Todos
async function fetchTodos() {
    const token = localStorage.getItem("jwt");

    if (!token) {
        window.location.href = "index.html";
        return;
    }

    try {
        const response = await fetch(`${API_URL}/todos`, {
            method: "GET",
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to fetch todos");
        }

        const todos = await response.json();
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = "";

        todos.forEach(todo => {
            const li = document.createElement("li");
            li.innerHTML = `
                ${todo.text}
                <button onclick="deleteTodo(${todo.id})">Delete</button>
            `;
            todoList.appendChild(li);
        });
    } catch (error) {
        alert("Failed to fetch todos. Please try again.");
    }
}

// Add New Todo
async function addTodo() {
    const token = localStorage.getItem("jwt");
    const text = document.getElementById("todo-input").value;

    if (!text) {
        alert("Please enter a todo.");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/todos`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ text })
        });

        if (!response.ok) {
            throw new Error("Failed to add todo");
        }

        document.getElementById("todo-input").value = "";
        fetchTodos();
    } catch (error) {
        alert("Failed to add todo. Please try again.");
    }
}

// Delete Todo
async function deleteTodo(id) {
    const token = localStorage.getItem("jwt");

    try {
        const response = await fetch(`${API_URL}/todos/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!response.ok) {
            throw new Error("Failed to delete todo");
        }

        fetchTodos();
    } catch (error) {
        alert("Failed to delete todo. Please try again.");
    }
}

// Load Todos when the dashboard page loads
if (window.location.pathname.includes("dashboard.html")) {
    fetchTodos();
}