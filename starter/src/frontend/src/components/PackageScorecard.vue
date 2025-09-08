<template>
  <div class="scorecard-container">
    <div v-if="packageData" class="package-scorecard">
      <!-- Header -->
      <div class="scorecard-header">
        <h1 class="package-name">{{ packageData.name }}</h1>
        <span class="package-version">v{{ packageData.version }}</span>
      </div>
      
      <p class="package-description">{{ packageData.description }}</p>
      
      <!-- Metrics Grid -->
      <div class="metrics-grid">
        <div class="metric-card">
          <div class="metric-icon">📦</div>
          <div class="metric-content">
            <h3>License</h3>
            <p class="metric-value">{{ packageData.license }}</p>
          </div>
        </div>
        
        <div class="metric-card">
          <div class="metric-icon">📊</div>
          <div class="metric-content">
            <h3>Weekly Downloads</h3>
            <p class="metric-value">{{ formatNumber(packageData.weeklyDownloads) }}</p>
          </div>
        </div>
        
        <div class="metric-card">
          <div class="metric-icon">🔗</div>
          <div class="metric-content">
            <h3>Dependencies</h3>
            <p class="metric-value">{{ packageData.dependencyCount }}</p>
          </div>
        </div>
        
        <div class="metric-card">
          <div class="metric-icon">💾</div>
          <div class="metric-content">
            <h3>Unpacked Size</h3>
            <p class="metric-value">{{ formatBytes(packageData.unpackedSize) }}</p>
          </div>
        </div>
        
        <div class="metric-card">
          <div class="metric-icon">📅</div>
          <div class="metric-content">
            <h3>Last Published</h3>
            <p class="metric-value">{{ formatDate(packageData.lastPublishDate) }}</p>
          </div>
        </div>
        
        <div class="metric-card">
          <div class="metric-icon">🏷️</div>
          <div class="metric-content">
            <h3>Available Tags</h3>
            <div class="tags">
              <span 
                v-for="(version, tag) in packageData.distTags" 
                :key="tag"
                class="tag"
                :class="{ 'tag-latest': tag === 'latest' }"
              >
                {{ tag }}: {{ version }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div v-else-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Analyzing package...</p>
    </div>
    
    <div v-else-if="error" class="error-state">
      <div class="error-icon">❌</div>
      <h3>Error</h3>
      <p>{{ error }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PackageScorecard',
  props: {
    packageData: {
      type: Object,
      default: null
    },
    isLoading: {
      type: Boolean,
      default: false
    },
    error: {
      type: String,
      default: ''
    }
  },
  methods: {
    formatNumber(num) {
      if (num === 0) return 'N/A';
      if (num >= 1000000) return (num / 1000000).toFixed(1) + 'M';
      if (num >= 1000) return (num / 1000).toFixed(1) + 'K';
      return num.toString();
    },
    
    formatBytes(bytes) {
      if (bytes === 0) return 'N/A';
      const sizes = ['Bytes', 'KB', 'MB', 'GB'];
      const i = Math.floor(Math.log(bytes) / Math.log(1024));
      return Math.round(bytes / Math.pow(1024, i) * 100) / 100 + ' ' + sizes[i];
    },
    
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
      });
    }
  }
}
</script>

<style scoped>
.scorecard-container {
  max-width: 1000px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.package-scorecard {
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  padding: 2rem;
  border: 1px solid #e1e8ed;
}

.scorecard-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
  flex-wrap: wrap;
}

.package-name {
  color: #2c3e50;
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0;
}

.package-version {
  background: linear-gradient(135deg, #3498db, #2980b9);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-weight: 600;
  font-size: 0.9rem;
}

.package-description {
  color: #7f8c8d;
  font-size: 1.1rem;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.metrics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.metric-card {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.metric-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.metric-icon {
  font-size: 2rem;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.metric-content h3 {
  color: #2c3e50;
  margin: 0 0 0.5rem 0;
  font-size: 0.9rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.metric-value {
  color: #34495e;
  margin: 0;
  font-size: 1.5rem;
  font-weight: 700;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.tag {
  background: #ecf0f1;
  color: #2c3e50;
  padding: 0.25rem 0.75rem;
  border-radius: 16px;
  font-size: 0.8rem;
  font-weight: 500;
}

.tag-latest {
  background: linear-gradient(135deg, #27ae60, #2ecc71);
  color: white;
}

.loading-state, .error-state {
  text-align: center;
  padding: 3rem;
  color: #7f8c8d;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #ecf0f1;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

@media (max-width: 768px) {
  .package-name {
    font-size: 2rem;
  }
  
  .metrics-grid {
    grid-template-columns: 1fr;
  }
  
  .metric-card {
    flex-direction: column;
    text-align: center;
  }
}
</style>
