const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

// module.exports = {
//   devServer: {
//       proxy: {
//           '/course-management': {
//               target: 'http://localhost:8080',
//               changeOrigin: true,
//               pathRewrite: {
//                   '^/course-management': '/course-management'
//               }
//           }
//       }
//   }
// }

