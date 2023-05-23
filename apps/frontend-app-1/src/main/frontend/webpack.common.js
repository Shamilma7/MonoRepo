const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');

module.exports = {
  entry: './src/index.tsx',
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: [MiniCssExtractPlugin.loader, 'css-loader'],
      },
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
  output: {
    publicPath: '/',
  },
  plugins: [
    new MiniCssExtractPlugin({ filename: 'bundle.[contenthash:8].css' }),
    new webpack.DefinePlugin({
      COMMIT_HASH: JSON.stringify(process.env.COMMIT_HASH || 'unknown'),
    }),
    new HtmlWebpackPlugin({
      favicon: 'public/favicon.ico',
      inject: true,
      template: 'public/index.html',
    }),
  ],
  resolve: {
    alias: {
      '~': path.resolve(__dirname, 'src'),
    },
    extensions: ['.tsx', '.ts', '.js'],
  },
};
