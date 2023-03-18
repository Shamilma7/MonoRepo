const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = (env) =>
  merge(common, {
    mode: 'development',
    devtool: 'eval-source-map',
    devServer: {
      historyApiFallback: true,
      port: 3000,
      open: {
        target: [`http://localhost:3000/logg-inn`],
      },
      client: {
        overlay: true,
      },
      proxy: {
        '/api': {
          target: env.backend,
          changeOrigin: true,
          secure: false,
        },
      },
    },
  });
