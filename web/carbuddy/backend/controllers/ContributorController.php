<?php

namespace backend\controllers;

use backend\models\Companies;
use backend\models\Contributors;
use backend\models\ContributorSearch;
use backend\models\Users;
use Yii;
use yii\helpers\VarDumper;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * ContributorController implements the CRUD actions for Contributors model.
 */
class ContributorController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all Contributors models.
     * @return mixed
     */
    public function actionIndex()
    {
        if (Yii::$app->user->can('backendCrudContributor')) {
            $searchModel = new ContributorSearch();
            $dataProvider = $searchModel->search($this->request->queryParams);

            return $this->render('index', [
                'searchModel' => $searchModel,
                'dataProvider' => $dataProvider,
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Displays a single Contributors model.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {

        if (Yii::$app->user->can('backendCrudContributor')) {
            return $this->render('view', [
                'model' => $this->findModel($id),
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Creates a new Contributors model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        if (Yii::$app->user->can('backendCrudContributor')) {
            $model = new Contributors();
            $modelUsers = Users::findAll(['usertype' => 'collaborator']);
            $modelCompanies = Companies::find()->all();

            if ($this->request->isPost) {
                if ($model->load($this->request->post()) && $model->save()) {
                    return $this->redirect(['view', 'id' => $model->id]);
                }
            } else {
                $model->loadDefaultValues();
            }
            return $this->render('create', [
                'model' => $model, 'modelUsers' => $modelUsers, 'modelCompanies' => $modelCompanies
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Updates an existing Contributors model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        if (Yii::$app->user->can('backendCrudContributor')) {
            $model = $this->findModel($id);
            $modelUsers = Users::findAll(['usertype' => 'collaborator']);
            $modelCompanies = Companies::find()->all();

            if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }

            return $this->render('update', [
                'model' => $model, 'modelUsers' => $modelUsers, 'modelCompanies' => $modelCompanies
            ]);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Deletes an existing Contributors model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        if (Yii::$app->user->can('backendCrudContributor')) {
            $this->findModel($id)->delete();
            return $this->redirect(['index']);
        } else {
            Yii::$app->user->logout();
            return $this->goHome();
        }
    }

    /**
     * Finds the Contributors model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Contributors the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected
    function findModel($id)
    {
        if (($model = Contributors::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
