<template>
  <div class="package-input">
    <h2>NPM Package Inspector</h2>
    <p class="subtitle">Enter a package name to analyze its dependencies and metrics</p>
    
    <form @submit.prevent="searchPackage" class="search-form">
      <div class="input-group">
        <input
          v-model="packageName"
          type="text"
          placeholder="e.g., react, express, lodash"
          class="package-input-field"
          :disabled="isLoading"
          required
        />
        <button 
          type="submit" 
          class="search-button"
          :disabled="isLoading || !packageName.trim()"
        >
          {{ isLoading ? 'Searching...' : 'Analyze Package' }}
        </button>
      </div>
    </form>

    <div v-if="error" class="error-message">
      {{ error }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'PackageInput',
  data() {
    return {
      packageName: '',
      isLoading: false,
      error: ''
    }
  },
  methods: {
    async searchPackage() {
      if (!this.packageName.trim()) return;
      
      this.isLoading = true;
      this.error = '';
      
      try {
        // Emit the search event to parent component
        this.$emit('search-package', this.packageName.trim());
      } catch (err) {
        this.error = 'Failed to search for package. Please try again.';
      } finally {
        this.isLoading = false;
      }
    }
  }
}
</script>

<style scoped>
.package-input {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
  text-align: center;
}

h2 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-size: 2.5rem;
  font-weight: 300;
}

.subtitle {
  color: #7f8c8d;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.search-form {
  margin-bottom: 1rem;
}

.input-group {
  display: flex;
  gap: 0.5rem;
  max-width: 500px;
  margin: 0 auto;
}

.package-input-field {
  flex: 1;
  padding: 1rem;
  border: 2px solid #e1e8ed;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

.package-input-field:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.search-button {
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #3498db, #2980b9);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 150px;
}

.search-button:hover:not(:disabled) {
  background: linear-gradient(135deg, #2980b9, #1f5f8b);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.3);
}

.search-button:disabled {
  background: #bdc3c7;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}

.error-message {
  background: #e74c3c;
  color: white;
  padding: 1rem;
  border-radius: 8px;
  margin-top: 1rem;
  font-weight: 500;
}

@media (max-width: 768px) {
  .input-group {
    flex-direction: column;
  }
  
  .search-button {
    min-width: auto;
  }
}
</style>
