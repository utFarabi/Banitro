package com.sm.banitro.ui.home.profile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sm.banitro.R;
import com.sm.banitro.data.model.Category;
import com.sm.banitro.data.model.Seller;
import com.sm.banitro.util.Permission;
import com.sm.banitro.util.Version;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private ProfileContract.Presenter iaPresenter;
    private Unbinder unbinder;
    private Uri cameraUri, cropPictureUri;

    // Data Type
    private final int IMAGE_REQUEST_CODE = 100;
    private int MAX_CROP_HEIGHT, MAX_CROP_WIDTH;

    // View
    @BindView(R.id.profile_fragment_iv_seller_image)
    ImageView ivSellerImage;
    @BindView(R.id.profile_fragment_tv_name)
    TextView tvName;
    @BindView(R.id.profile_fragment_tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.profile_fragment_tv_address)
    TextView tvAddress;
    @BindView(R.id.profile_fragment_tv_category)
    TextView tvCategory;
    @BindView(R.id.profile_fragment_pb_progress)
    ProgressBar pbProgress;

    // ********************************************************************************
    // New Instance

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (Interaction) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iaPresenter = new ProfilePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        setValueToMaxCropDimens();

        iaPresenter.loadData();
    }

    // ********************************************************************************
    // Initialization

    private void setValueToMaxCropDimens() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        MAX_CROP_WIDTH = size.x * 5;
        MAX_CROP_HEIGHT = size.y * 2;
    }

    // ********************************************************************************
    // Method

    private boolean isAllPermissionGranted() {
        return Permission.iscameraPermissionGranted(getContext())
                && Permission.isStoragePermissionGranted(getContext());
    }

    private void loadImageFromGalleryOrCamera() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, true);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Version.isVersionCodesKitkatSupported()) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        cameraUri = createImageUri(getContext());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        Intent[] intents = new Intent[]{cameraIntent};

        Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.choose_your_profile_image));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        startActivityForResult(chooserIntent, IMAGE_REQUEST_CODE);
    }

    private Uri createImageUri(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        contentValues.put(MediaStore.Images.Media.TITLE, timeStamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private boolean isRequestPermissionResultGranted(int requestCode, int[] grantResults) {
        return requestCode == Permission.IMAGE_PERMISSION_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED;
    }

    private void handleGalleryResult(Intent data) {
        File sourceFile = null;
        File destinationFile = null;
        try {
            String realPathFromURI = getRealPathFromURI(getContext(), data.getData());
            if (Version.isVersionCodesKitkatSupported()) {
                if (realPathFromURI == null) {
                    sourceFile = new File(getImageUrlWithAuthority(getContext(), data.getData()));
                } else {
                    sourceFile = new File(realPathFromURI);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error on Read Image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
        try {
            destinationFile = createImageTempFile();
            cropPictureUri = Uri.fromFile(destinationFile);
            if (sourceFile.exists()) {
                if (Version.isVersionCodesMSupported()) {
                    showCropperDialog(FileProvider.getUriForFile(getContext(),
                            getContext().getPackageName() + ".provider", sourceFile),
                            cropPictureUri);
                } else {
                    showCropperDialog(Uri.fromFile(sourceFile), cropPictureUri);
                }
            } else {
                showCropperDialog(data.getData(), cropPictureUri);
            }
        } catch (Exception e) {
            if (destinationFile != null) {
                destinationFile.delete();
            }
            Toast.makeText(getContext(), "Error on Write Image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void showCropperDialog(Uri sourceImage, Uri destinationImage) {
        CropImage
                .activity(sourceImage)
                .setAllowFlipping(false)
                .setAllowRotation(true)
                .setAllowCounterRotation(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .setMaxCropResultSize(MAX_CROP_WIDTH, MAX_CROP_HEIGHT)
                .start(getContext(), this);
    }

    private void handleCameraResult(Uri cameraPictureUrl) {
        try {
            cropPictureUri = Uri.fromFile(createImageFile());
            showCropperDialog(cameraPictureUrl, cropPictureUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getRealPathFromURI(Context context, final Uri uri) {

        if (Version.isVersionCodesKitkatSupported() && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else
            return getRealPathFromURIDB(context, uri);

        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private String getRealPathFromURIDB(Context context, Uri contentUri) {
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String realPath = cursor.getString(index);
            cursor.close();
            return realPath;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageTempFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                createRootDirectory()      /* directory */
        );
    }

    private File createRootDirectory() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getString(R.string.app_name));

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return mediaStorageDir;
    }

    // ********************************************************************************
    // Implement

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void showSeller(Seller seller) {
        tvName.setText(seller.getName());
        tvPhoneNumber.setText(seller.getPhoneNumber());
        tvAddress.setText(seller.getAddress());
        ArrayList<Category> categories = seller.getCategories();
        String categoriesName = "";
        for (int i = 0; i < categories.size(); i++) {
            categoriesName += categories.get(i).getName() + ",";
        }
        tvCategory.setText(categoriesName);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSellerImage(File file) {
        Glide.with(this).load(file.getPath()).into(ivSellerImage);
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.profile_fragment_iv_seller_image)
    public void onClickSellerImage() {
        if (Version.isVersionCodesMSupported()) {
            if (isAllPermissionGranted()) {
                loadImageFromGalleryOrCamera();
            } else {
                requestPermissions(Permission.IMAGE_PERMISSIONS, Permission.IMAGE_PERMISSION_REQUEST_CODE);
            }
        } else {
            loadImageFromGalleryOrCamera();
        }
    }

    @OnClick(R.id.profile_fragment_cl_name)
    public void onClickName() {
        interaction.goToEditTextDialogFragment(R.string.full_name);
    }

    @OnClick(R.id.profile_fragment_cl_phoneNumber)
    public void onClickPhoneNumber() {
        interaction.goToEditTextDialogFragment(R.string.phone);
    }

    @OnClick(R.id.profile_fragment_cl_address)
    public void onClickAddress() {
        interaction.goToEditTextDialogFragment(R.string.address);
    }

    @OnClick(R.id.profile_fragment_cl_category)
    public void onClickCategory() {
        interaction.goToEditCategoryDialogFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isRequestPermissionResultGranted(requestCode, grantResults)) {
            loadImageFromGalleryOrCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getData() != null) {
                    handleGalleryResult(data);
                }
            } else {
                handleCameraResult(cameraUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                iaPresenter.uploadSellerImage(new File(result.getUri().getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interaction = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iaPresenter = null;
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void goToEditTextDialogFragment(int type);

        void goToEditCategoryDialogFragment();
    }
}