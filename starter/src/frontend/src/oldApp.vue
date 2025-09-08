<template>
  <div id="app">
    <div class="app-container">
      <PackageInput @search-package="handlePackageSearch" />
      <PackageScorecard 
        :package-data="packageData"
        :is-loading="isLoading"
        :error="error"
      />
    </div>
  </div>
</template>

<script>
import PackageInput from './components/PackageInput.vue'
import PackageScorecard from './components/PackageScorecard.vue'
import axios from 'axios'

export default {
  name: 'App',
  components: {
    PackageInput,
    PackageScorecard
  },
  data() {
    return {
      packageData: null,
      isLoading: false,
      error: ''
    }
  },
  methods: {
    async handlePackageSearch(packageName) {
      this.isLoading = true;
      this.error = '';
      this.packageData = null;
      
      try {
        const response = await axios.get(`/api/packages/${packageName}`);
        this.packageData = response.data;
      } catch (err) {
        if (err.response && err.response.status === 404) {
          this.error = `Package "${packageName}" not found. Please check the package name and try again.`;
        } else if (err.response && err.response.status >= 500) {
          this.error = 'NPM Registry API is currently unavailable. Please try again later.';
        } else {
          this.error = 'Failed to fetch package data. Please try again.';
        }
      } finally {
        this.isLoading = false;
      }
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
  color: #2c3e50;
}

#app {
  min-height: 100vh;
  padding: 2rem 0;
}

.app-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
</style>
