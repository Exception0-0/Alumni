# Use the official Python image as the base
FROM python:3.11-slim

# Set the working directory
WORKDIR /app

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application code
COPY . .

# Expose the port (optional, if your app listens on a specific port)
EXPOSE 5000

# Define the default command to run your application
CMD ["python", "app.py"]
