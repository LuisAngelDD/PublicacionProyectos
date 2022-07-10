const cloudinary = require('cloudinary')
const {cloud_name,cloud_api_key,cloud_api_secret} = require('./config')
cloudinary.v2.config({ 
    cloud_name: cloud_name, 
    api_key: cloud_api_key, 
    api_secret: cloud_api_secret 
  });
const uploadImg = async (filePath) =>{
    return await cloudinary.v2.uploader.upload(filePath,{folder:'usersImg'});
}
const deletImg = async (publicId) =>{
  return await cloudinary.v2.uploader.destroy(publicId);
}
const uploadPDF = async (filePath) =>{
  return await cloudinary.v2.uploader.upload(filePath,{folder:'usersPdf'});
}
const deletPDF = async (publicId) =>{
  return await cloudinary.v2.uploader.destroy(publicId);
}
module.exports = {uploadImg,deletImg,uploadPDF,deletPDF}