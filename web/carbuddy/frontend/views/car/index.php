<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\CarSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */
$this->title = 'Vehicles';
?>
<div class="cars-index">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Vehicle', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            //'id',
            'registration',
            'vin',
            'brand',
            'model',
            //'color',
            //'carType',
            //'displacement',
            //'fuelType',
            'modelyear',
            //'kilometers',
            //'state',
            //'userId',

            ['class' => 'yii\grid\ActionColumn',
                'template' => '{view} {update} {delete} {repair}',  // the default buttons + your custom button
                'buttons' => [
                    'repair' => function ($url, $model, $key) {     // render your custom button
                        return Html::a('<svg xmlns="http://www.w3.org/2000/svg" width="14" fill="currentColor" class="bi bi-info-circle-fill" viewBox="0 0 16 16">
  <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
</svg>', ['repairs/history', 'car' => $key], ['class' => 'profile-link']);
                    }
                ]],
        ],
    ]); ?>


</div>
