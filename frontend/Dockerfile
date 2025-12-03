FROM node:18-alpine

WORKDIR /app

# Install curl for healthcheck
RUN apk add --no-cache curl

# Copy package files (including package-lock.json)
COPY package.json package-lock.json* ./

# Install dependencies
# Use npm ci for faster, reliable, reproducible builds
# Fallback to npm install if package-lock.json doesn't exist
RUN if [ -f package-lock.json ]; then npm ci --omit=dev; else npm install --omit=dev; fi

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Expose port (Railway will set PORT env var)
EXPOSE 3000

# Start the application
# Use PORT environment variable if set (for Railway), otherwise default to 3000
CMD ["sh", "-c", "next start -p ${PORT:-3000}"]
