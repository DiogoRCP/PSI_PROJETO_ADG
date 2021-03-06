<?php

namespace frontend\models;

use Yii;
use yii\helpers\VarDumper;

/**
 * This is the model class for table "cars".
 *
 * @property int $id
 * @property string $vin
 * @property string $brand
 * @property string $model
 * @property string $color
 * @property string $carType
 * @property float $displacement
 * @property string $fuelType
 * @property string $registration
 * @property string $modelyear
 * @property int $kilometers
 * @property string $state
 * @property int $userId
 *
 * @property Repairs[] $repairs
 * @property Users $user
 */
class Cars extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'cars';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['vin', 'brand', 'model', 'color', 'carType', 'displacement', 'fuelType', 'registration', 'modelyear', 'kilometers', 'state', 'userId'], 'required'],
            [['displacement'], 'number'],
            [['modelyear'], 'safe'],
            [['kilometers', 'userId'], 'integer'],
            [['vin', 'brand', 'model', 'color', 'carType', 'fuelType', 'registration', 'state'], 'string', 'max' => 100],
            [['vin'], 'unique'],
            [['userId'], 'exist', 'skipOnError' => true, 'targetClass' => Users::className(), 'targetAttribute' => ['userId' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'vin' => 'VIN',
            'brand' => 'Brand',
            'model' => 'Model',
            'color' => 'Color',
            'carType' => 'Car Type',
            'displacement' => 'Displacement',
            'fuelType' => 'Fuel Type',
            'registration' => 'Registration',
            'modelyear' => 'Year',
            'kilometers' => 'Kilometers',
            'state' => 'State',
            'userId' => 'User',
        ];
    }

    public function getSchedules()
    {
        return Schedules::find()->where("carId = " . $this->id)->orderBy("schedulingdate")->all();
    }

    /**
     * Gets query for [[Repairs]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRepairs()
    {
        return $this->hasMany(Repairs::className(), ['carId' => 'id']);
    }

    /**
     * Gets query for [[User]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(Users::className(), ['id' => 'userId']);
    }

    /**
     * @return string
     */
    public function getCarType()
    {
        $query = $this::find()
            ->where(['cartype' => $this->carType])
            ->count('cartype');
        return $query;
    }

    public function veicleImage($size)
    {
        switch ($this->carType) {
            case 'PASSENGER CAR':
                return '
                <svg style="fill: ' . $this->color . ';" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1141.67 387.4" width="' . $size . '" height="' . ($size / 2) . '">
<title>' . $this->color . '</title>
<path d="M1364.2,432.3v26.6c-.1.9-.3,1.8-.4,2.7-.5,4.8-.9,9.6-1.4,14.4a3.12,3.12,0,0,1-1.6,2.1,27.3,27.3,0,0,0-12.7,10c-3.1,4.7-5.7,9.8-8.5,14.8-2.3,4.1-4.4,8.4-7.1,12.3-3.8,5.6-9.5,7.9-16.2,7.6-6.1-.3-12.2-.6-18.3-.6q-38.4.15-76.8.6c-2.3,0-3.1-.4-3.6-2.8-1.9-9-3.9-18.1-6.6-26.9-5.9-19.4-14.3-37.5-27.1-53.4-28.4-35.4-70.1-36.6-100.3-20.6-16.8,8.9-28.7,22.7-38,39-11.1,19.5-17.8,40.5-21.3,62.5-.4,2.2-1.3,2.4-3.1,2.4H524c-.3-2.8-.6-5.3-.9-7.8-2.5-20.5-8-40-18.2-58.1-20.8-37-54.5-50.2-92.2-45.5-23.8,2.9-42.4,15-56.6,34-14.3,19.2-21.8,41.3-26.2,64.5-.8,4.2-1.3,8.4-1.9,12.6H262.8c-.6-3.5-.9-7-1.7-10.2-2.6-10.5-8.7-18.9-16.1-26.4-5.6-5.6-11.4-11-17.2-16.5-3.8-3.7-5.9-7.6-5.1-13.4,2.1-15.8,5.1-31.4,11.2-46.2,6-14.4,14.6-27.3,23.4-40.1a45.39,45.39,0,0,1,15.8-14.4c3.7-2,7.3-4.1,11.1-6,27.2-13.2,56.2-20.4,86-24.2,24.4-3.1,49-5.3,73.5-7.6,22.9-2.2,45.9-4.3,68.7-8.2A220.54,220.54,0,0,0,553,298.9c10.2-3.9,19-10.3,28-16.4,41.7-28.6,84.6-55.3,127.4-82.1a12.27,12.27,0,0,1,2.6-1.2c12.1-4.8,24.8-7.1,37.6-8.8,22.7-3,45.6-4,68.5-4.3,24.1-.3,48.2-.4,72.3-.6,2.2,0,4.4-.3,6.6-.4h26.6a15.17,15.17,0,0,0,2.1.3c20.2,1.6,40.5,2.9,60.6,4.9a632.1,632.1,0,0,1,74.7,12.3c21.4,4.8,41.9,11.6,60.8,23,11.4,6.9,23.1,13.5,34.4,20.6,29.6,18.4,59,37,88.5,55.7a114.77,114.77,0,0,0,37.2,15.5,337.05,337.05,0,0,1,49.1,14.5,3.11,3.11,0,0,1,2.3,2.6,282.32,282.32,0,0,0,18.8,54.4c4.4,9.7,8.7,19.5,10.8,30.1C1362.9,423.4,1363.5,427.9,1364.2,432.3Z" transform="translate(-222.53 -185.1)"/>
<path d="M1197.4,496.5c.1,41.8-34,75.9-75.9,76a76,76,0,1,1,75.9-76Z" transform="translate(-222.53 -185.1)"/><path d="M426.7,420.5a76,76,0,1,1-76,76A76.12,76.12,0,0,1,426.7,420.5Z" transform="translate(-222.53 -185.1)"/>
</svg>';
                break;
            case 'MOTORCYCLE':
                return '
                <svg style="fill: ' . $this->color . ';" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 638.66 342.07" width="' . $size . '" height="' . ($size / 2) . '">
                <title>' . $this->color . '</title>
                <path d="M462,389.64c.81-1.6,2.12-3,2.34-4.59a79.92,79.92,0,0,1,16.36-36.88c21.09-26.95,48.78-39.46,83.16-36.71a64.75,64.75,0,0,1,10.89,1.47c1.3.31,3.28,3.33,2.87,4.22-3.76,8.68-7.82,17.15-12.28,25.52-.81,1.49-3.42,2.67-5.32,3-7.71,1.14-15.71.89-23,3-19.25,5.66-31.34,19.27-37.77,37.93-5.62,16.06-3.23,31.48,5,46.14a58.05,58.05,0,0,0,78.44,23.35c35-18.75,42-62.9,15.35-92.09-2.48-2.72-2.77-5-1.25-8,3.45-6.78,6.9-13.65,10-20.53s3.85-7.07,9.51-1.93c17.79,16.12,28.55,35.7,31.48,59.82a90.4,90.4,0,0,1-11.6,57c-13.37,23.31-33.37,38.17-59.72,44.28-19.73,4.56-39.22,3.52-57.57-4.71-29.8-13.51-49-36.15-54.91-69.09-.29-1.4-1.48-2.71-2.17-4.11C461.83,407.74,461.9,398.74,462,389.64Z" transform="translate(-461.77 -158.44)"/><path d="M1100.43,411.16a91.4,91.4,0,0,1-4.7,28.37,85.14,85.14,0,0,1-13.27,23.9c-13.73,17.81-31.22,29.78-53.55,34.63-16.73,3.68-33.33,3.36-49.29-1.85-21.65-7.06-39.26-20.28-50.82-40.16-5.13-8.74-8.36-18.76-11.79-28.39-2-5.81-.25-7.5,5.85-7.46,6.7.05,13.39.6,20-.06,6.1-.65,8.78,2.17,10.25,7.38a13.77,13.77,0,0,0,.58,1.9c8.57,19.36,23.08,31.77,43.75,35.61,16.58,3,32.11-.67,46.18-10.87,24.13-17.63,30.69-54.38,13.56-78.91-13.36-19.09-31.5-28.82-54.62-26-9.71,1.23-15.29-.81-19.33-9.64-2.76-6.22-6.91-11.95-10.77-18.38,21.77-9,43.27-9.89,64.22-2.74a107.73,107.73,0,0,1,18.15,8,83.7,83.7,0,0,1,28.8,27c7.82,12,13.92,25.5,15.92,39.71A134.37,134.37,0,0,1,1100.43,411.16Z" transform="translate(-461.77 -158.44)"/><path d="M934.09,317.18c-7.39,12.55-14.78,25.2-22.17,37.74-6.78,11.56-13.56,23.11-20.35,34.76-2,3.49-1.13,5.09,3.07,5.42,1.49.11,3,0,4.5,0l123.39.88c11.6.18,11.31-1.42,11.12,11.58-.15,7.5-.15,7.5-7.55,7.44-47-.33-93.9-.56-140.9-1.09-5.6,0-8.51,1.94-11.54,6.41-8.9,13.34-21.56,21.25-37.86,21.34-25.9.11-51.9-.27-77.89-.46-11.9-.08-23.71-.06-35.5-.35-1.7,0-4.2-.83-4.89-2.13-3.06-5.52-5.72-11.24-8.38-17q-16.41-34.47-32.81-69.14c-5.61-11.94-11.23-23.88-17.24-36.52,2.7-.18,4.8-.57,6.9-.55l54.5.39a16.79,16.79,0,0,0,4.5-.27c.91-.3,2.12-1.59,2-2.29a4.1,4.1,0,0,0-2-2.81,12.72,12.72,0,0,0-4.4-.33l-59.5-.42c-5.6,0-7.28-2-7.54-8.26,1.4-.09,2.9-.18,4.3-.17l91.9.65a19.56,19.56,0,0,0,5.5-.26,3.78,3.78,0,0,0,2.22-2.58,3.57,3.57,0,0,0-2.18-2.62,15.78,15.78,0,0,0-4.9-.33c-25.9-.19-51.9-.47-77.9-.55-15.5,0-30.9.28-45.76-5.43-5.68-2.14-7.59-.65-10.24,5.13-6.09,13.06-12.08,26.11-18.27,39.17-12.19,25.61-24.57,51.13-36.65,76.74-1.52,3.29-3.42,3.78-6.22,2.66a37.5,37.5,0,0,1-8.07-3.86c-1.39-1-2.77-3.82-2.26-5,3.56-8.38,7.51-16.55,11.47-24.82,7.41-15.45,14.92-31,22.33-46.55,9-19,18-38.07,27-57.11,9.23-19.33,18.57-38.57,27.71-57.9,8.12-17.25,16-34.59,24.37-51.73,4.36-9.07,8.37-10,18-7.07,12.47,3.89,25.14,7.38,37.82,10.57,4.39,1.13,5.88,2.64,3.95,6.82a14.64,14.64,0,0,0-.92,2.9c-2.06,8.48-3.56,9-12,6.31-9.18-2.86-18.57-5-27.85-7.6-3.09-.82-5.2-.33-6.62,3.16-2.65,6.48-5.79,12.66-8.84,18.94s-2.84,6.68,4,7.42c20.19,2.35,40.27,4.39,60.35,7.43A179.26,179.26,0,0,1,782,234c13.45,7,25.59,15.68,33,29.33,2,3.72,4.26,4.83,8.16,4.86,24.8.08,49.6.35,74.4.43a14.72,14.72,0,0,0,6.31-1c9.83-4.73,19.47-10.06,29.4-14.59,8.13-3.74,16.36-8.28,25-9.32,23.32-2.74,46.73-4.17,70.14-5.5,12.81-.71,24,12.67,21.72,25.25-.62,3.5.16,5.7,2.65,7.92,8.84,8.06,17.58,16.22,26.42,24.29,6.56,5.94,13.12,12,20.67,18.94-2.72,2.58-4.73,4.67-7,6.85-15.4-14.1-29.7-27.51-44.31-40.51a12.37,12.37,0,0,0-8.38-2.86c-14.91,1.39-29.73,3.29-44.54,5.18-13.91,1.71-27.73,3.61-41.54,5.51-5.41.76-6.72,3.45-4,7.87,9.5,15.37,19.09,30.74,28.58,46.11,8.5,13.66,17,27.42,25.41,41.08,2.47,3.92,1.76,5.41-2.74,5.58a90.09,90.09,0,0,1-11.5-.18,7.22,7.22,0,0,1-4.88-2.44c-6.83-10.55-13.25-21.39-19.88-32.14-8.7-14.06-17.5-28.12-26.59-42.89C936.91,313.9,935.4,315.39,934.09,317.18Z" transform="translate(-461.77 -158.44)"/><path d="M574.55,209c1.85-7.19,7.08-11.15,12.87-9.81a45.66,45.66,0,0,1,12.07,4.29c11.35,6.18,17.15,20.22,14.06,33.4-2.89,12.48-15,22.49-27.56,23-4.41.17-8.2-1.36-9.56-5.67-2.45-7.91-4.39-16.13-6.24-23C571.75,222.71,572.7,215.82,574.55,209Z" transform="translate(-461.77 -158.44)"/><path d="M964,364.19c.69,1.11,0,3.6-.84,5.1-3,5.48-6.18,10.85-9.71,16-1.22,1.79-3.73,3.57-5.73,3.66-8.9.33-17.9,0-26.9,0-3.6,0-4.49-1.73-3.27-4.83,2.65-7,4.9-14.16,8.25-20.74a81,81,0,0,1,21.49-26.55C953.18,346.42,958.72,355.26,964,364.19Z" transform="translate(-461.77 -158.44)"/></svg>
                ';
                break;

            case 'TRUCK':
                return '
                <svg style="fill: ' . $this->color . ';" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 593.77 300.84" width="' . $size . '" height="' . ($size / 2) . '">
                <title>' . $this->color . '</title>
                <path d="M118.07,328c-2.63-.62-5.29-1.15-7.9-1.87a46,46,0,1,1,19.49,1.2,17.5,17.5,0,0,0-2.32.67Zm4.28-21.08a25,25,0,1,0,.28-50,25,25,0,1,0-.28,50Z" transform="translate(-9.63 -27.16)"/>
                <path d="M438.88,328c-5.29-1.68-10.87-2.74-15.81-5.14-19.69-9.58-29.46-32.21-23.48-53.36,6.1-21.59,26.12-35.13,49.09-33.2,21,1.77,38.6,19.21,40.95,40.53a45.91,45.91,0,0,1-38.45,50.49,17.54,17.54,0,0,0-2.32.68Zm30.06-45.89a25.07,25.07,0,0,0-50.14-.46,25.07,25.07,0,0,0,50.14.46Z" transform="translate(-9.63 -27.16)"/>
                <path d="M223.55,27.33H603.33v4q0,97.07.06,194.15c0,3.2-.77,4.19-4.11,4.19q-185.7-.14-371.4,0c-3.34,0-4.41-.78-4.41-4.28q.17-97.06.08-194.15Z" transform="translate(-9.63 -27.16)"/><path d="M185.67,281.65A63,63,0,0,0,123,219.5c-17.17,0-32,6-44.32,18.05C67.53,248.49,63.88,257,59.51,281.71H9.63V278q0-48.7,0-97.41a38.06,38.06,0,0,1,1.06-7.69c5.86-27.67,16.45-53.88,25-80.72,4-12.41,8.15-24.75,12.42-37.05a7.64,7.64,0,0,1,3.45-4C67.52,43.39,83.5,35.81,99.47,28.23a10,10,0,0,1,4.06-1q50.43-.09,100.85,0c.58,0,1.15.08,2.09.15v217H393.81C385.43,255.88,381.3,268,381,281.65ZM40.8,152.24H176.89V77.71a25.13,25.13,0,0,0-2.58-.29q-52.92,0-105.85-.07c-2.5,0-3.16,1.16-3.82,3.14q-8.55,26-17.23,52C45.24,138.94,43.07,145.43,40.8,152.24Z" transform="translate(-9.63 -27.16)"/>
                <path d="M506.65,281.88a63.28,63.28,0,0,0-12.41-37.19h87.94c0,8.2,0,16.23-.07,24.26,0,.72-.91,1.57-1.59,2.13-1.28,1-2.86,1.75-4,2.89-7.36,7.08-16.16,8.56-26.1,8.07-13.15-.66-26.36-.16-39.55-.16Z" transform="translate(-9.63 -27.16)"/></svg>
                ';
                break;

            case 'MULTIPURPOSE PASSENGER VEHICLE (MPV)':
                return '
                <svg style="fill: ' . $this->color . ';" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 559.05 209.07" width="' . $size . '" height="' . ($size / 2) . '">
                <title>' . $this->color . '</title>
                <path d="M583.12,150c-16.58.05-33.16.16-49.73,0-1.18,0-2.95-1.72-3.41-3-3.33-9.23-9.16-16.53-18.23-20.24a110,110,0,0,0-23.5-6.66c-24.19-4-45.78,6.5-57.9,27.27a6.59,6.59,0,0,1-4.82,2.67q-113.59.19-227.19.17c-2.88,0-4.22-1.06-5.33-3.51a42.26,42.26,0,0,0-14.84-17.38c-13.74-9.55-29.25-11.47-45.36-9.5-14.23,1.73-26.83,7.11-35.67,18.92-9.07,12.13-11.44,26-10.37,40.7.17,2.28.3,4.56.43,6.55-11.43,0-22.29.1-33.15-.09a9.43,9.43,0,0,1-5.77-2.06c-7.92-7.36-15.59-15-23.34-22.52q.25-10.33.5-20.67a16.17,16.17,0,0,1,.17-2.2c1.33-8.25-.13-6.95,7.86-7.08,2.54,0,5.08,0,7.62,0,13.3,0,14.9-1.64,14.54-14.86-.18-6.56-3-9.36-9.66-9.44-4.09,0-8.17,0-12.83,0,7-15.14,16.2-26.51,32.76-29.95,4.67-1,9.31-2.29,14-2.73,10.66-1,20.07-4.77,29-10.46,17.46-11.06,35.44-21.35,52.75-32.64a125.38,125.38,0,0,1,70.39-21c53.77,0,107.54-1.26,161.3-1.63,43.62-.3,87.24.21,130.85-.2,16.38-.15,27.48,6.76,34,21a181.23,181.23,0,0,1,8.17,22c2.1,6.74,2.31,14.25,5.36,20.46,3.72,7.58,4.93,15.31,5.92,23.37.72,5.85,0,12.17,5.52,17.7h-5.71c-6,0-12-.06-17.92.09-3.42.09-6.12,2-5.87,5.37.16,2.19,2.13,4.53,3.88,6.18.93.88,3.13.54,4.76.55,7,0,14,0,21.58,0v24.47A3.17,3.17,0,0,1,583.12,150ZM341.36,82.11V27.55c-1.46-.09-2.91-.24-4.35-.24-22.83,0-45.66.13-68.49-.09-4.1,0-6,1.54-6.17,5.08-.71,16.45-1.22,32.91-1.81,49.81ZM137,82.31c26.25,0,52.5-.08,78.75,0,3,0,4.68-.81,5.51-3.69,2.69-9.31,6.3-18.46,8-27.94,1.31-7.39.26-15.18.26-23-18.69-1.44-37.2-.43-54.51,9.26C155,48.13,138.2,63,123,79.76a21.84,21.84,0,0,0-1.62,2.55ZM367.28,27.47V81.94c1.23.1,2.38.29,3.53.27q30.19-.36,60.4-.77c2.18,0,4.13.12,4.12-3.06-.09-16.79,0-33.58,0-50.91ZM460.4,81.18c3.6-.18,7-.19,10.32-.55,13.92-1.49,28-2,41.7-4.89,18.87-3.92,28.34-30.18,17.31-45.76-.9-1.26-2.74-2.67-4.15-2.67-21.35-.11-42.7,0-64.05.07-.26,0-.53.24-1.13.52Z" transform="translate(-24.94 -8.48)"/><path d="M423,162.54H198.49v23.89c1.51.07,3,.2,4.4.2q107.06.13,214.11.25c5.94,0,5.95,0,5.95-6S423,168.88,423,162.54Z" transform="translate(-24.94 -8.48)"/><path d="M479.4,217.53c-23.66.58-43.74-18.72-44.22-42.5-.48-23.48,18.95-43.17,43.05-43.64a43.49,43.49,0,0,1,44.18,42.48C523,197.34,503.64,216.94,479.4,217.53Zm-.34-18.22a24.45,24.45,0,1,0-24.63-24.56A24.58,24.58,0,0,0,479.06,199.31Z" transform="translate(-24.94 -8.48)"/><path d="M99.17,175c-.46-23.48,19-43.12,43.11-43.56A43.54,43.54,0,0,1,186.41,174c.41,23.5-19,43.1-43.13,43.59A43.56,43.56,0,0,1,99.17,175Zm44.29,24.36a24.45,24.45,0,1,0-25-24.16A24.53,24.53,0,0,0,143.46,199.31Z" transform="translate(-24.94 -8.48)"/><path d="M534,162.54c.47,8,.92,15.54,1.43,24.19,7.65,0,15.2-.1,22.73,0,12.77.2,12.77.25,17.75-11.67,1.69-4,3.41-8.06,5.3-12.54Z" transform="translate(-24.94 -8.48)"/></svg>
                ';
                break;
            default:
                return '
                <svg style="fill: ' . $this->color . ';" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1141.67 387.4" width="' . $size . '" height="' . ($size / 2) . '">
<title>' . $this->color . '</title>
<path d="M1364.2,432.3v26.6c-.1.9-.3,1.8-.4,2.7-.5,4.8-.9,9.6-1.4,14.4a3.12,3.12,0,0,1-1.6,2.1,27.3,27.3,0,0,0-12.7,10c-3.1,4.7-5.7,9.8-8.5,14.8-2.3,4.1-4.4,8.4-7.1,12.3-3.8,5.6-9.5,7.9-16.2,7.6-6.1-.3-12.2-.6-18.3-.6q-38.4.15-76.8.6c-2.3,0-3.1-.4-3.6-2.8-1.9-9-3.9-18.1-6.6-26.9-5.9-19.4-14.3-37.5-27.1-53.4-28.4-35.4-70.1-36.6-100.3-20.6-16.8,8.9-28.7,22.7-38,39-11.1,19.5-17.8,40.5-21.3,62.5-.4,2.2-1.3,2.4-3.1,2.4H524c-.3-2.8-.6-5.3-.9-7.8-2.5-20.5-8-40-18.2-58.1-20.8-37-54.5-50.2-92.2-45.5-23.8,2.9-42.4,15-56.6,34-14.3,19.2-21.8,41.3-26.2,64.5-.8,4.2-1.3,8.4-1.9,12.6H262.8c-.6-3.5-.9-7-1.7-10.2-2.6-10.5-8.7-18.9-16.1-26.4-5.6-5.6-11.4-11-17.2-16.5-3.8-3.7-5.9-7.6-5.1-13.4,2.1-15.8,5.1-31.4,11.2-46.2,6-14.4,14.6-27.3,23.4-40.1a45.39,45.39,0,0,1,15.8-14.4c3.7-2,7.3-4.1,11.1-6,27.2-13.2,56.2-20.4,86-24.2,24.4-3.1,49-5.3,73.5-7.6,22.9-2.2,45.9-4.3,68.7-8.2A220.54,220.54,0,0,0,553,298.9c10.2-3.9,19-10.3,28-16.4,41.7-28.6,84.6-55.3,127.4-82.1a12.27,12.27,0,0,1,2.6-1.2c12.1-4.8,24.8-7.1,37.6-8.8,22.7-3,45.6-4,68.5-4.3,24.1-.3,48.2-.4,72.3-.6,2.2,0,4.4-.3,6.6-.4h26.6a15.17,15.17,0,0,0,2.1.3c20.2,1.6,40.5,2.9,60.6,4.9a632.1,632.1,0,0,1,74.7,12.3c21.4,4.8,41.9,11.6,60.8,23,11.4,6.9,23.1,13.5,34.4,20.6,29.6,18.4,59,37,88.5,55.7a114.77,114.77,0,0,0,37.2,15.5,337.05,337.05,0,0,1,49.1,14.5,3.11,3.11,0,0,1,2.3,2.6,282.32,282.32,0,0,0,18.8,54.4c4.4,9.7,8.7,19.5,10.8,30.1C1362.9,423.4,1363.5,427.9,1364.2,432.3Z" transform="translate(-222.53 -185.1)"/>
<path d="M1197.4,496.5c.1,41.8-34,75.9-75.9,76a76,76,0,1,1,75.9-76Z" transform="translate(-222.53 -185.1)"/><path d="M426.7,420.5a76,76,0,1,1-76,76A76.12,76.12,0,0,1,426.7,420.5Z" transform="translate(-222.53 -185.1)"/>
</svg>';
        }
    }

    //Fun????es e metodos dos testes
    public function setvin($vin)
    {
        $this->vin = $vin;
    }

    public function setbrand($brand)
    {
        $this->brand = $brand;
    }

    public function setmodel($model)
    {
        $this->model = $model;
    }

    public function setcolor($color)
    {
        $this->color = $color;
    }

    public function setcartype($cartype)
    {
        $this->carType = $cartype;
    }

    public function setdisplacement($displacement)
    {
        $this->displacement = $displacement;
    }

    public function setfueltype($fueltype)
    {
        $this->fuelType = $fueltype;
    }

    public function setregistration($registration)
    {
        $this->registration = $registration;
    }

    public function setmodelyear($modelyear)
    {
        $this->modelyear = $modelyear;
    }

    public function setkilometers($kilometers)
    {
        $this->kilometers = $kilometers;
    }

    public function setstate($state)
    {
        $this->state = $state;
    }

    public function setuserid($userid)
    {
        $this->userId = $userid;
    }
}
