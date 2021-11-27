<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel backend\models\ContributorSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */


$this->title = 'Contributors';
?>
<div class="contributors-index">
    <h1><?= Html::encode($this->title) ?></h1>
    <p>
        <?= Html::a('Add Contributor', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php //echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            'userId',
            'companyId',
            'speciality',
            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
