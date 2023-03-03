module.exports = {
    publicPath: './',
    lintOnSave: false,
    devServer: {
        open: true,
        host: 'localhost',
        port: 8080,
        https: false,
        hotOnly: false,
        proxy: { // 配置跨域
            '/api': {
                target: 'http://localhost:8070/',
                ws: true,
                changOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            },
            '/cm': {
                target: 'http://localhost:8081/',
                ws: true,
                changOrigin: true,
                pathRewrite: {
                    '^/cm': ''
                }
            },
            before: app => {}
        }
    }
}